import styled from "styled-components";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
//images
import main from "../assets/images/main.jpg";
import googleStoreButton from "../assets/images/google-play-badge.png";
import leaf from "../assets/images/leaf.jpg";
import phone from "../assets/images/phone.png";
import leaf2 from "../assets/images/leaf2.png";

//memberPhotos
import Buhee from "../assets/images/Buhee.png";
import Juhyo from "../assets/images/Juhyo.jpg";
import Dasol from "../assets/images/Dasol.jpg";
import Gyuyeon from "../assets/images/Gyuyeon.jpg";
import Sojeong from "../assets/images/Sojeong.jpg";
import Gisung from "../assets/images/Gisung.jpg";

//main
import {
  MainImage,
  ServiceTitle,
  ServiceSummary,
} from "../assets/style/mainSection";

//video section
import {
  LeftBgImage,
  RightBgImage,
  VideoDescription,
} from "../assets/style/videoSection";

//member section
import {
  MadeBy,
  BottomLefgBgImage,
  TopRightBgImage,
  MemberPhoto,
  MemberTitle,
  MemberRole,
  MemberGithub,
  MemberRow,
} from "../assets/style/memberSection";

import { Num, DescTitle, DescDetail } from "../assets/style/descriptionSection";

// common
const Section = styled.section`
  height: 100vh;
  width: 100vw;
  position: relative;
`;

function Main() {
  return (
    <div>
      <Section>
        <MainImage src={main} alt="main" />
        <ServiceTitle>Green Fingers</ServiceTitle>
        <ServiceSummary>
          <div>
            반려식물 키우기,
            <br /> 그린핑거스와 함께하세요.
          </div>
          <Grid
            container
            justify="center"
            align="center"
            width="100%"
            spacing={5}
          >
            <Grid item xs={4} justify="center" align="center">
              <a href="https://play.google.com/store/apps/details?id=com.kimnpark.greenfingers">
                <img src={googleStoreButton} alt="google download"></img>
              </a>
            </Grid>
            <Grid item xs={4} justify="center" align="center">
              <a href="">
                <div
                  style={{
                    textAlign: "center",
                    justifyContent: "center",
                    alignSelf: "center",
                    lineHeight: 2.5,
                  }}
                >
                  App 다운로드
                </div>
              </a>
            </Grid>
          </Grid>
        </ServiceSummary>
      </Section>
      <Section
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <LeftBgImage src={leaf} alt="left Video Bg"></LeftBgImage>
        <RightBgImage src={leaf} alt="right Video Bg"></RightBgImage>
        <VideoDescription>
          그린핑거스를 영상으로 <br />
          확인해보세요.
        </VideoDescription>
        <iframe
          width="360"
          height="640"
          src="https://www.youtube.com/embed/R3Mrl-rthso?rel=0"
          frameborder="0"
          allow="autoplay; encrypted-media"
          allowfullscreen=""
          title="video"
        ></iframe>
      </Section>
      <Section
        style={{
          display: "flex",
        }}
      >
        <div style={oddDescWrap}>
          <div>
            <Num>01</Num>
            <DescTitle>
              나의 식물을 등록 후, <br />
              진짜 나의 집처럼 꾸며보세요.
            </DescTitle>
          </div>
          <DescDetail>
            식물의 사진을 찍어 인식하고, 진짜 나의 집처럼 식물을 방에 배치해
            보세요. 진짜 나의 방과 어울리는 테마를 적용해 관리 할 수 있습니다.
          </DescDetail>
        </div>
        <div style={oddImageWrap}>
          <img src={phone} alt="phone"></img>
        </div>
      </Section>

      <Section style={{ display: "flex" }}>
        <div style={evenImageWrap}>
          <img
            src={phone}
            alt="phone"
            style={{ display: "inline-block", justifyContent: "flex-end" }}
          ></img>
        </div>
        <div style={evenDescWrap}>
          <div>
            <Num>02</Num>
            <DescTitle>
              달력에서 물 준 날짜,
              <br /> 식물 일기를 한 눈에 확인해보세요.
            </DescTitle>
          </div>
          <DescDetail>
            각 식물의 물 준 날짜를 기록하고, 식물의 성장 다이어리를 작성해서
            식물의 성장 과정을 기록하세요.
          </DescDetail>
        </div>
      </Section>
      <Section
        style={{
          display: "flex",
        }}
      >
        <div style={oddDescWrap}>
          <div>
            <Num>03</Num>
            <DescTitle>
              AR 기능으로 새로운 식물을 <br />
              우리집에 배치해보세요.
            </DescTitle>
          </div>
          <DescDetail>
            새로운 우리집 반려식물, 여기저기 옮겨다니면서 확인해 보지 않아도
            돼요. 식물을 들이기 전에 AR로 먼저 확인해 보세요!
          </DescDetail>
        </div>
        <div style={oddImageWrap}>
          <img src={phone} alt="phone"></img>
        </div>
      </Section>

      <Section style={{ display: "flex" }}>
        <div style={evenImageWrap}>
          <img
            src={phone}
            alt="phone"
            style={{ display: "inline-block", justifyContent: "flex-end" }}
          ></img>
        </div>
        <div style={evenDescWrap}>
          <div>
            <Num>04</Num>
            <DescTitle>
              MBTI별로 <br />
              맞춤 식물을 추천해드려요.
            </DescTitle>
          </div>
          <DescDetail>
            많은 관심과 손길을 필요하는 식물, 혼자서도 씩씩하게 잘 자라는 식물,
            다양한 식물의 세계. <br />
            나의 MBTI에 꼭 맞는 식물을 찾아드립니다.
          </DescDetail>
        </div>
      </Section>
      <Section>
        <BottomLefgBgImage src={leaf2}></BottomLefgBgImage>
        <TopRightBgImage src={leaf2}></TopRightBgImage>
        <MadeBy>만든 사람들</MadeBy>
        <MemberRow
          container
          justify="center"
          align="center"
          style={{ paddingTop: 200 }}
        >
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Dasol}></MemberPhoto>
            <MemberTitle>박다솔</MemberTitle>
            <MemberRole>Frontend & Team Leader</MemberRole>
            <MemberGithub href="https://github.com/Park-Dasol">
              https://github.com/Park-Dasol
            </MemberGithub>
          </Grid>
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Gyuyeon}></MemberPhoto>
            <MemberTitle>김규연</MemberTitle>
            <MemberRole>Frontend & AR</MemberRole>
            <MemberGithub href="https://github.com/qqyurr">
              https://github.com/qqyurr
            </MemberGithub>
          </Grid>
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Buhee}></MemberPhoto>
            <MemberTitle>김부희</MemberTitle>
            <MemberRole>Backend & AI</MemberRole>
            <MemberGithub href="https://github.com/buri-1029">
              https://github.com/buri-1029
            </MemberGithub>
          </Grid>
        </MemberRow>
        <MemberRow container justify="center" align="center">
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Sojeong}></MemberPhoto>
            <MemberTitle>김소정</MemberTitle>
            <MemberRole>Frontend & AR</MemberRole>
            <MemberGithub href="https://github.com/itoggi03">
              https://github.com/itoggi03
            </MemberGithub>
          </Grid>
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Juhyo}></MemberPhoto>
            <MemberTitle>김주효</MemberTitle>
            <MemberRole>Backend & AI</MemberRole>
            <MemberGithub href="https://github.com/kjuhyo">
              https://github.com/kjuhyo
            </MemberGithub>
          </Grid>
          <Grid item xs={3} style={memberWrap}>
            <MemberPhoto src={Gisung}></MemberPhoto>
            <MemberTitle>박기성</MemberTitle>
            <MemberRole>Backend & CI/CD</MemberRole>
            <MemberGithub href="https://github.com/gisungPark">
              https://github.com/gisungPark
            </MemberGithub>
          </Grid>
        </MemberRow>
      </Section>
    </div>
  );
}

export default Main;

const oddDescWrap = {
  flex: 2,
  flexDirection: "column",
  alignSelf: "center",
  paddingLeft: "12%",
};

const oddImageWrap = {
  flex: 1,
  alignSelf: "center",
  paddingLeft: "7%",
  paddingRight: "5%",
  justifyContent: "center",
};

const evenDescWrap = {
  flex: 2,
  alignSelf: "center",
  paddingRight: "12%",
};

const evenImageWrap = {
  flex: 1,
  alignSelf: "center",
  paddingLeft: "11%",
  justifyContent: "center",
};

const memberWrap = {
  jusitifyContent: "center",
  alignItems: "center",
  flexDirection: "column",
};
