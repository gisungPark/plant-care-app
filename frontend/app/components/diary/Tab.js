import {
  Container,
  Header,
  Tab,
  Tabs,
  ScrollableTab,
  Thumbnail,
  TabHeading,
} from "native-base";

export function PlantTab() {
  return (
    <Tab
      heading={
        <TabHeading
          style={{
            backgroundColor: "#F9F9F9",
          }}
        >
          <Thumbnail
            small
            source={{
              uri:
                "https://mblogthumb-phinf.pstatic.net/MjAxODA2MzBfNTkg/MDAxNTMwMzI2NjQwOTIx.V2Vi9fi-77A0y4gFgpDi150YjEGnd2xk9H4y0MCUAmMg.tiU5xMaA44sUGo_XaQGii4mcqKGJExz2Q7lIzNyjEx8g.JPEG.flowervine/DSC00041.JPG?type=w800",
            }}
          />
        </TabHeading>
      }
    ></Tab>
  );
}

// export default PlantTab;
