# CalendarPageView

a quick,simple,custom month view 

##Preview

![https://raw.githubusercontent.com/Kyson/CalendarPageView/master/images/preview.png](https://raw.githubusercontent.com/Kyson/CalendarPageView/master/images/preview.png)

## Usage

- Setup Gradle

```gradle
dependencies {
    compile 'com.tt:calendarpageview:1.0.0'
}
```

- Like the use of the other widgets.

```xml
<com.tt.calendarpageview.CalendarPageView
        android:id="@+id/wtf"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/primary"></com.tt.calendarpageview.CalendarPageView>
```

## Customization

One of the aims of this library is to be customizable.

Options available in XML attributes:

|Attribute          |Type     |Description                                         |
|---                |---      |---                                                 |
|weekTextStyle      |style    |Set weekday title text style                        |
|todayTextStyle     |style    |Set today text style                                |
|notCurrentTextStyle|style    |Set text style of the day out of current shown month|
|dayTextStyle       |style    |Common  day text style                              |
|daySelector        |drawable |Day click selector                                  |

## API available

|Method	                 |Description                      |
|---                     |---                              |
|setCurrentMonth()	     |Set the monthView's current month|
|selectCal()	         |Select day                       |
|setOnDaySelectListener()|Set the callback of select day   |

## About me

- [Kyson's Blog](http://www.hikyson.cn)
- [Kyson's OSC](http://git.oschina.net/cocobaby)
- [Follow Me](http://weibo.com/1980495343/profile?rightmod=1&wvr=6&mod=personinfo)

## License

Copyright (c) 2015 Kyson

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)