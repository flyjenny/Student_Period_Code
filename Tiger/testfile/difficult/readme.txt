在BT文件夹中为编号大于1000的testcase,由于对于其中一些数据的处理有二义性,所以没有对数据加以整理

在TIG下为无名测试数据，如果都可以过掉应该至少有85%（个人估计:)）的代码是正确的
在ABS下为相应数据的输出AST（也就是说，数据没有词法错和语法错），请大家帮忙对一下

下面是每个程序的注释（在文件开头有）：

test1: Basic Dec Test
test2: recursive dec
test3: empty program
test4: Basic break
test5: basic nil
test6: basic nil (2)
test7: constant for variable
test8: void check
test9: pre defined function 
test10: basic binary operator
test11: non-associate the expr included in the /* */ will report syntax error
test12: basic & | () and prec please check the ast
test13: basic lvalue
test14: basic expr
test15: Basic Scope
test16: By Jobo

由于时间原因，原定的词法和语法部分都没有加入测试集。同样原因，我把很多类型相近的情况都放到了一起，所以这里对那些只能报出一个错的同学们说声道歉。
虽然说提交的版本要求TC(type-checking)过后再输出.abs，但由于几乎所有的testcase都有TC错，但AST还是可以正确生成的，所以麻烦大家帮忙看下AST是不是正确的。

最后，希望大家一起来完成我们的第一个软件大作业！并祝大家顺利通过！