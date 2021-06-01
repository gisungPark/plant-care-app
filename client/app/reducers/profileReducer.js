const UPDATE_PROFILE_NICKNAME = 'UPDATE_PROFILE_NICKNAME';
const UPDATE_PROFILE_IMAGE = 'UPDATE_PROFILE_IMAGE';
const SET_PROFILE = 'SET_PROFILE';
const SET_USER_ID = 'SET_USER_ID';
const CLEAR_USER = 'CLEAR_USER';

export const updateProfileNickname = nickname => ({
  type: UPDATE_PROFILE_NICKNAME,
  nickname,
});
export const updateProfileImage = image => ({
  type: UPDATE_PROFILE_IMAGE,
  image,
});
export const setProfile = (profile, provider, useremail) => ({
  type: SET_PROFILE,
  profile,
  provider,
  useremail,
});
export const setUserID = userId => ({
  type: SET_USER_ID,
  userId,
});
export const clearUser = () => ({
  type: CLEAR_USER,
});

const profileState = {
  nickname: '그린핑거스',
  profile:
    'https://www.sketchappsources.com/resources/source-image/profile-illustration-gunaldi-yunus.png',
  thema: 'DEAFULT_THEMA_IMAGE',
  provider: '',
  userId: '',
  useremail: '',
};

export default function profile(state = profileState, action) {
  // console.log('redux userID ', state.userId);
  switch (action.type) {
    case 'SET_PROFILE':
      return {
        ...action.profile,
        provider: action.provider,
        useremail: action.useremail,
      };
    case 'UPDATE_PROFILE_NICKNAME':
      return {
        ...state,
        nickname: action.nickname,
      };
    case 'UPDATE_PROFILE_IMAGE':
      return {
        ...state,
        profile: action.image,
      };
    case 'SET_USER_ID':
      return {
        ...state,
        profile: action.userId,
      };
    case 'CLEAR_USER':
      return {
        nickname: '그린핑거스',
        profile:
          'https://www.sketchappsources.com/resources/source-image/profile-illustration-gunaldi-yunus.png',
        thema: 'DEAFULT_THEMA_IMAGE',
        provider: '',
        userId: '',
      };
    default:
      return state;
  }
}
