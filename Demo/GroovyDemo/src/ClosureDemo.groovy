/**
 * Created by zhengchen on 12/10/2017.
 */
class ClosureDemo {
    static void main(args) {
        closureDef()
        closureParam()
        closureInMethod()
        closureInCollection()
    }

    public static closureDef() {
        def clos = { println "Hello World" };
        clos.call();//结果打印Hello World
    }

    public static closureParam() {
        def clos = { param -> println "Hello ${param}" };
        clos.call("World");//结果打印Hello World

        def clos1 = { println "Hello ${it}" };//it是Groovy的一个关键字，代表隐式单个参数
        clos1.call("World");//结果打印Hello World

        def str1 = "Hello";
        def clos2 = { paramA -> println "${str1} ${paramA}" }
        str1 = "Welcome"
        clos2.call("World")//结果打印Welcome World
    }

    public static closureInMethod() {
        display({ param -> println "Hello ${param}" })
    }

    def static display(clo) {
        clo.call("Inner")
    }


    public static closureInCollection() {
        //List
        def lst = [11, 12, 13, 14];
        lst.each { println it }//it 是默认的param
        //Map
        def mp = ["TopicName": "Maps", "TopicDescription": "Methods in Maps"]
        mp.each { println "${it.key} maps to: ${it.value}" }
    }


}
