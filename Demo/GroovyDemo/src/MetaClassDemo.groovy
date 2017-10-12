/**
 * Created by zhengchen on 12/10/2017.
 */
class MetaClassDemo {
    static void main(args) {

        stringMeta()
        metaClassDemo()
    }

    static class Student {
        String name
    }

    public static stringMeta() {
        String.metaClass.hello = { ->
            println "Hello ${delegate}"
        }

        String me = "ChenZheng"
        me.hello()
    }


    public static metaClassDemo() {
        Student.metaClass.nameInUpperCase = { -> delegate.name.toUpperCase() }
        def b = new Student(name: "Tom")
        println b.nameInUpperCase()
    }


}
