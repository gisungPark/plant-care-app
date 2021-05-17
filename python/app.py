from flask import Flask, jsonify, render_template, request, redirect, send_file
import io

import os
import numpy as np
import matplotlib.pyplot as plt
import torch
from torch import nn
from torch import optim
import torch.nn.functional as F
from torch.autograd import Variable
import torchvision
from torchvision import datasets, transforms, models
from collections import OrderedDict
import json
import time
from PIL import Image
import argparse

app = Flask(__name__)

DEFAULT_DATA_DIRECTORY = 'custom_set'
DEFAULT_CHECKPOINT_FILENAME = './checkpoint2.pth'
DEFAULT_TOP_K = 3
DEFAULT_GPU = True
FILENAME_JSON_CATEGORY = 'cat_to_name.json'
FILEPATH_JSON_CATEGORY = DEFAULT_DATA_DIRECTORY + '/' + FILENAME_JSON_CATEGORY
SIZE_CROP = 224
SIZE_RESIZE = 256
NORMALIZE_MEAN = [0.485, 0.456, 0.406]
NORMALIZE_STD = [0.229, 0.224, 0.225]


def parse_input_arguments():
    parser = argparse.ArgumentParser(description = "Predict using a deep neural network")
    parser.add_argument('--image_path', type = str, default ="", help = 'Dataset path')
    parser.add_argument('--checkpoint_path', type = str, default = DEFAULT_CHECKPOINT_FILENAME, help = 'Path to load trained model checkpoint')
    parser.add_argument('--top_k', type = int, default = DEFAULT_TOP_K, help = 'Top K most likely classes')
    parser.add_argument('--category_names', type = str, default = FILEPATH_JSON_CATEGORY, help = 'File .json for the mapping of categories to real names')
    parser.add_argument('--gpu', action = "store_true", default = DEFAULT_GPU, help = 'Use GPU if available')

    args = parser.parse_args()

    return args.image_path, args.checkpoint_path, args.top_k, args.category_names, args.gpu


def load_model_checkpoint(file_path):
    checkpoint = torch.load(file_path)
    learning_rate = checkpoint['learning_rate']
    model = getattr(torchvision.models, checkpoint['network'])(pretrained = True)
    model.classifier = checkpoint['classifier']
    model.epochs = checkpoint['epochs']
    model.optimizer = checkpoint['optimizer']
    model.load_state_dict(checkpoint['state_dict'])
    model.class_to_idx = checkpoint['class_to_idx']
    
    return model


def process_image(pil_image):
    img_loader = transforms.Compose([transforms.Resize(SIZE_RESIZE),
                                    transforms.CenterCrop(SIZE_CROP), 
                                    transforms.ToTensor()])
    
    pil_image = img_loader(pil_image).float()
    
    np_image = np.array(pil_image)    
    
    mean = np.array(NORMALIZE_MEAN)
    std = np.array(NORMALIZE_STD)
    np_image = (np.transpose(np_image, (1, 2, 0)) - mean) / std    
    np_image = np.transpose(np_image, (2, 0, 1))
            
    return np_image


def get_prediction(image_path, model, top_k_probabilities = DEFAULT_TOP_K):
    # Use GPU if it's available
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

    model.to(device)
    model.eval()

    pil_image = Image.open(image_path)
    
    np_image = process_image(pil_image)
    tensor_image = torch.from_numpy(np_image)
    
    inputs = Variable(tensor_image)
    
    if torch.cuda.is_available():
        inputs = Variable(tensor_image.float().cuda())           
        
    inputs = inputs.unsqueeze(dim = 0).float()
    log_probabilities = model.forward(inputs)
    probabilities = torch.exp(log_probabilities)    

    top_probabilities, top_classes = probabilities.topk(top_k_probabilities, dim = 1)
    class_to_idx_inverted = {model.class_to_idx[c]: c for c in model.class_to_idx}
    top_mapped_classes = list()
    
    for label in top_classes.cpu().detach().numpy()[0]:
        top_mapped_classes.append(class_to_idx_inverted[label])
    
    return top_probabilities.cpu().detach().numpy()[0], top_mapped_classes


def get_mapping_label_name_categories(category_names):
    print('\t' + category_names)
    with open(category_names, 'r') as f:
        category_label_to_name = json.load(f)
    return category_label_to_name


def load_model(category_names, checkpoint_path):
    print('Load the model checkpoint from {}'.format(checkpoint_path))
    model = load_model_checkpoint(checkpoint_path)

    print('Load category name and label mapping')
    category_label_to_name = get_mapping_label_name_categories(category_names)

    return model, category_label_to_name


def predict(image_path, checkpoint_path, top_k, category_names, gpu):
    model, category_label_to_name = load_model(category_names, checkpoint_path)

    top_probabilities, top_classes = get_prediction(image_path, model, top_k_probabilities = top_k)

    print('Probabilities: ', top_probabilities)
    print('Categories:    ', top_classes)
    print('Result:    ', top_classes[0])
    return top_classes[0]

######################################################################################################

@app.route("/")
def main():
    return render_template("upload.html")

@app.route('/predict', methods=['POST'])
def result_predict():
    image_path, checkpoint_path, top_k, category_names, gpu = parse_input_arguments()
    
    # 이미지 받아오기
    f = request.files['file']

    # 분류 결과 확인 및 클라이언트에게 결과 반환
    class_name = predict(f, checkpoint_path, top_k, category_names, gpu)
    print("결과:", {'class_name': class_name})
    response = {'result' : class_name}
    return render_template("result.html", name=f.filename, result=class_name) # 로컬 테스트용
    # return jsonify(response) # 배포용

if __name__ == "__main__":
    app.run(host='0.0.0.0',port=4000)
