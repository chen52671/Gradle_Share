/**
 * Created by zhengchen on 19/09/2017.
 */
class HelloWorld {
    static void main(args) {
        demoVariable()
        demoQuoted()
        demoStrings()
        demoStringInterpolation()
        demoCycle()
    }

    def static demoVariable(clo) {
        def name = "Grooy"
        String name1 = "Java"
        println name
        println name1
    }

    def static demoQuoted() {
        def map = [:]
        map."show-add-when-open" = "ALLOWED"
        map.'with-dash-signs-and-single-quotes' = "ALLOWED"
        println map

    }

    def static demoStrings() {
        def name = 'Groovy'//java.lang.String 不支持插值
        def strippedFirstNewline = '''line one
line two
line three
'''//java.lang.String，支持多行
        def normalStr = "Groovy" //java.lang.String
        def interpolatedStr = "my name is ${normalStr}" //groovy.lang.GString-字符串插值

        println strippedFirstNewline
        println interpolatedStr.class.name

    }

    def static demoStringInterpolation() {

        def person = [name: 'Groovy', age: 28]
        println "$person.name is $person.age years old"

        def pi = 3.14
        println "Pi = ${pi.toString()}"
    }


    def static demoCycle() {
        def count = 0
        //while循环
        while (count < 5) {
            println(count)
            count++
        }
        //for循环
        for (int i = 0; i < 5; i++) {
            println(i)
        }
        //for-in 循环
        int[] array = [0, 1, 2, 3]
        for (int i in array) {
            println(i)
        }
    }

}

/*
----Import省略
public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}
*
* */