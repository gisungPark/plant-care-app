import React, {useState} from 'react';

// native-base
import {Icon} from 'native-base';

// calendar
import {Calendar} from 'react-native-calendars';
import {Modal, View} from 'react-native';
import DiarySelectModal from './modal/DiarySelectModal';
import CheckDateModal from './modal/CheckDateModal';
import CompleteModal from './modal/CompleteModal';

export function CalendarView(props) {
  const [modalVisible, setModalVisible] = useState(false);
  const [dateCheckModalVisible, setDateCheckModalVisible] = useState(false);
  const [completeModalVisible, setCompleteModalVisible] = useState(false);

  const [selectDay, setSelectDay] = useState('');
  const [selectMonth, setSelectMonth] = useState('');
  const [selectYear, setSelectYear] = useState('');

  return (
    <View>
      <Calendar
        theme={{
          'stylesheet.day.basic': {
            base: {
              width: 32,
              height: 60,
              justifyContent: 'center',
              alignItems: 'center',
            },
          },
          'stylesheet.dot': {
            dot: {
              width: 7,
              height: 7,
              borderRadius: 5,
            },
          },
        }}
        // Initially visible month. Default = Date()
        current={'2021-04-20'}
        // Minimum date that can be selected, dates before minDate will be grayed out. Default = undefined
        minDate={'2021-01-01'}
        // Maximum date that can be selected, dates after maxDate will be grayed out. Default = undefined
        maxDate={'2021-12-31'}
        // Handler which gets executed on day press. Default = undefined
        onDayPress={day => {
          // console.log('selected day', day);
          setModalVisible(!modalVisible);
          // setSelectDate(day.dateString);
          setSelectDay(day.day);
          setSelectMonth(day.month);
          setSelectYear(day.year);
          // console.log(day);
        }}
        // Handler which gets executed on day long press. Default = undefined
        onDayLongPress={day => {
          // console.log('selected day', day);
        }}
        // Month format in calendar title. Formatting values: http://arshaw.com/xdate/#Formatting
        monthFormat={'yyyy . MM'}
        // Handler which gets executed when visible month changes in calendar. Default = undefined
        onMonthChange={month => {
          // console.log('month changed', month);
        }}
        // Hide month navigation arrows. Default = false
        hideArrows={false}
        // Replace default arrows with custom ones (direction can be 'left' or 'right')
        renderArrow={direction =>
          direction == 'left' ? (
            <Icon
              type="Entypo"
              name="chevron-thin-left"
              style={{fontSize: 20, color: '#29582C'}}
            />
          ) : (
            <Icon
              type="Entypo"
              name="chevron-thin-right"
              style={{fontSize: 20, color: '#29582C'}}
            />
          )
        }
        // Do not show days of other months in month page. Default = false
        hideExtraDays={false}
        // If hideArrows=false and hideExtraDays=false do not switch month when tapping on greyed out
        // day from another month that is visible in calendar page. Default = false
        disableMonthChange={false}
        // If firstDay=1 week starts from Monday. Note that dayNames and dayNamesShort should still start from Sunday.
        firstDay={7}
        // Hide day names. Default = false
        hideDayNames={false}
        // Show week numbers to the left. Default = false
        showWeekNumbers={false}
        // Handler which gets executed when press arrow icon left. It receive a callback can go back month
        onPressArrowLeft={subtractMonth => subtractMonth()}
        // Handler which gets executed when press arrow icon right. It receive a callback can go next month
        onPressArrowRight={addMonth => addMonth()}
        // Disable left arrow. Default = false
        disableArrowLeft={false}
        // Disable right arrow. Default = false
        disableArrowRight={false}
        // Disable all touch events for disabled days. can be override with disableTouchEvent in markedDates
        disableAllTouchEventsForDisabledDays={true}
        // Replace default month and year title with custom one. the function receive a date as parameter.
        // renderHeader={(date) => {
        //   return (
        //   );
        // }}
        // Enable the option to swipe between months. Default = false
        enableSwipeMonths={false}
        markedDates={{
          '2021-04-18': {marked: true, dotColor: '#8AD169'},
          '2021-04-19': {marked: true, dotColor: '#8AD169'},
        }}
      />

      {/* 다이어리 보기/다이어리 작성/물주기 선택 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
          setDateCheckModalVisible(false);
        }}>
        <DiarySelectModal
          setModalVisible={setModalVisible}
          setDateCheckModalVisible={setDateCheckModalVisible}
          navigation={props.navigation}
        />
      </Modal>

      {/* 날짜 확인 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={dateCheckModalVisible}
        onRequestClose={() => {
          setDateCheckModalVisible(!dateCheckModalVisible);
        }}>
        <CheckDateModal
          setDateCheckModalVisible={setDateCheckModalVisible}
          setCompleteModalVisible={setCompleteModalVisible}
          // 선택한 일, 월, 년 데이터 넘기기
          selectDay={selectDay}
          selectMonth={selectMonth}
          selectYear={selectYear}
        />
      </Modal>

      {/* 물주기 완료 모달 */}
      <Modal
        animationType="fade"
        transparent={true}
        visible={completeModalVisible}
        onRequestClose={() => {
          setCompleteModalVisible(!completeModalVisible);
        }}>
        <CompleteModal
          content="물주기 완료💧"
          setDateCheckModalVisible={setDateCheckModalVisible}
          setCompleteModalVisible={setCompleteModalVisible}
        />
      </Modal>
    </View>
  );
}