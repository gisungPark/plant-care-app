import {instance} from './index';
import {instanceFile} from './index';
import {instanceRecognition} from './index';

// 나의 식물 조회 기반 등록
export function myPlantRegister(formData) {
  return instanceFile.post('plant/care', formData);
}

// 나의 식물 이미지 분류 기반 등록
export function myPlantIdentiyRegister(common) {
  return instanceFile.post(`plant/care/${common}`);
}

// 나의 식물 상세 정보
export function myPlantInfo(pid) {
  return instance.get(`plant/care/${pid}`);
}

// 나의 식물 수정
export function myPlantEdit(pid, formData) {
  return instanceFile.put(`plant/care/${pid}`, formData);
}

// 나의 식물 떠나감
export function myPlantDead(pid) {
  return instance.put(`plant/care/dead/${pid}`);
}

// 나의 식물 삭제
export function myPlantDelete(pid) {
  return instance.put(`plant/care/delete/${pid}`);
}

// 물 준 날짜 등록
export function myPlantWaterRegister(parmas) {
  return instance.post('plant/care/water', parmas);
}

// 식물의 모든 물 준 날짜 조회
export function myPlantWaterInfo(pid) {
  return instance.get(`plant/care/water/${pid}`);
}

// 물 준 날짜 취소
export function myPlantWaterCancel(wid) {
  return instance.delete(`plant/care/water/${wid}`);
}

// 모든 식물 이름 조회(autocomplete를 위한 API)
export function plantAll() {
  return instance.get('plant/info');
}

// 식물 이름 조회
export function plantSearch(search) {
  return instance.get(`plant/info/${search}`);
}

// 식물 상세 정보 조회
export function plantInfoDetail(id) {
  return instance.get(`plant/info/detail/${id}`);
}

//식물 판별
export function plantRecognition(formData) {
  return instanceRecognition.post('predict', formData);
}

//학명 식물 상세 정보 조회
export function plantInfoDetailByName(common) {
  return instance.get(`plant/check/${common}`);
}
