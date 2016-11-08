## Synopsis

Get app download from iTunes via Reporter Tool.

## Code Example

Take a look on tests, I've already written enough test cases, you can know how to use this project via them.

## Motivation

This project help you have an example how to get app download from iTunes via using Reporter Tool and Java language.
It just gets the Sales report, for others report type, please take a look on http://help.apple.com/itc/contentreporterguide/#/itcbe21ac7db
It's also include some useful methods like:
    . Read output in console log
    . Parse string XML to Document.
    . How to test private method using Reflection
    . Mocking use PowerMock

## Installation

You just need to
    . change your apple id and password in Reporter.properties file
    . change app store URL of the app you want to get download number
    . change vendor id, which you can get by using command line: $ java -jar Reporter.jar p=Reporter.properties m=Robot.XML Sales.getVendors. Pick a respectively vendor id with your app from the list of vendor ids
I also using Lombok to auto generate Getter, Setter, Builder pattern. You need to install Lombok plugin on you IDE to use getter, setter and builder of objects.

## License

You can use this project or anything inside it on your demands.
