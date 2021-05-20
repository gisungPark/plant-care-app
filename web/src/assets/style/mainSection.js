import styled from "styled-components";

// Main
export const MainImage = styled.img`
  width: 100vw;
  height: 100vh;
  object-fit: cover;
  object-position: 100% 60%;
  overflow: hidden;
  position: absolute;

  top: 0;
  left: 0;
  z-index: -1;
`;

export const ServiceTitle = styled.h1`
  color: #29582c;
  position: absolute;
  font-size: 40px;
  top: 7%;
  left: 15%;
`;

export const ServiceSummary = styled.div`
  position: absolute;
  top: 40%;
  right: 5%;
  width: 50vw;
  div {
    color: #323232;
    text-align: center;
    font-weight: 600;
    font-size: 55px;
  }
  a {
    margin-top: 15%;
    /* margin-left: 3%;
    margin-right: 3%; */
    border: none;
    background-color: transparent;
    display: inline-block;
    color: none;
    position: relative;
    width: 100%;
    height: 75%;
    img {
      height: 100%;
      width: 100%;
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
    }
    div {
      height: 100%;
      width: 100%;
      border: 1px solid black;
      font-size: 20px;
      border-radius: 5px;
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
    }
    :hover {
      cursor: pointer;
    }
  }
`;
