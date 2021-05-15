from flask import Flask, render_template, request, redirect, send_file
from PIL import Image

import io
import json
import os

import torch
import torch.nn as nn
import torch.optim as optim

import torchvision
from torchvision import datasets, models, transforms

import numpy as np
import time
import os

app = Flask(__name__)

device = torch.device("cuda:0" if torch.cuda.is_available()
                      else "cpu")  # device 객체

transforms_train = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.RandomHorizontalFlip(),  # 데이터 증진(augmentation)
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [
                         0.229, 0.224, 0.225])  # 정규화(normalization)
])

transforms_test = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
])

data_dir = './custom_dataset'
train_datasets = datasets.ImageFolder(
    os.path.join(data_dir, 'train'), transforms_train)
test_datasets = datasets.ImageFolder(
    os.path.join(data_dir, 'test'), transforms_test)

class_names = train_datasets.classes


@app.route("/")
def main():
    return render_template("upload.html")

# 이미지를 읽어 결과를 반환하는 함수


def get_prediction(image_bytes):
    # state_dict만 저장
    model = models.resnet34(pretrained=True)
    num_features = model.fc.in_features
    model.fc = nn.Linear(num_features, 208)
    model.load_state_dict(torch.load('weight_only1.pth', map_location=device))
    # 모델 전체 저장
    # model = torch.load('./entire_model1.pth',map_location=device)
    image = Image.open(io.BytesIO(image_bytes)).convert('RGB')
    image = transforms_test(image).unsqueeze(0).to(device)

    with torch.no_grad():
        outputs = model(image)
        _, preds = torch.max(outputs, 1)
    return class_names[preds[0]]


@app.route('/predict', methods=['POST'])
def predict():
    # 이미지 바이트 데이터 받아오기
    f = request.files['file']
    image_bytes = f.read()

    # 분류 결과 확인 및 클라이언트에게 결과 반환
    class_name = get_prediction(image_bytes=image_bytes)
    print("결과:", {'class_name': class_name})
    return render_template("result.html", name=f.filename, result=class_name)


# 웹 서버 생성
if __name__ == '__main__':
    app.run(host='0.0.0.0',port=4000)
