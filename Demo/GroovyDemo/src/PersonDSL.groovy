/**
 * Created by zhengchen on 20/09/2017.
 */
class PersonDSL {
    def dslScriptCode1 = '''
    person {
        name {
            firstname 'DSL'
            lastname  'Groovy'
        }
        action{
          eat "apple"
          drive "mini cooper"
        }
    }   
    '''

    def dslScriptCode2 = '''
    person {
        name {
            firstname = 'DSL'
            lastname  = 'Groovy'
        }
        action{
          eat = "apple"
          drive = "mini cooper"
        }
    }   
    '''

    def scriptClosure = { Closure personClosure ->
        def person = new Person(new Name(), new Action())
        personClosure.delegate = new PersonDelegate(person)
        personClosure.resolveStrategy = Closure.DELEGATE_FIRST
        personClosure()

        return person
    }

    def execute() {
        def person = executeScript(dslScriptCode2, 'person', scriptClosure)
        println person
    }

    def createMetaClass(Class clazz, Closure closure) {
        // 为传入的Class对象创建一个ExpandoMetaClass实例，但不将该ExpandoMetaClass实例注册到MetaClassRegistry对象中
        def emc = new ExpandoMetaClass(clazz, false)
        //该closure用来初始化ExpandoMetaClass对象
        closure(emc)
        // 完成初始化过程
        emc.initialize()
        return emc
    }

    def executeScript(dslScriptCode, rootName, closure) {
        Script dslScript = new GroovyShell().parse(dslScriptCode)  // 读取并解析DSL代码，返回一个Script对象

        dslScript.metaClass = createMetaClass(dslScript.class) { emc ->
            //动态新增一个名为"$rootName"的方法，注意"$rootName"的值决定于运行时，比如本例中的值为person
            emc."$rootName" = closure
        }

        return dslScript.run() // 执行DSL代码
    }

    static void main(args) {
        PersonDSL personDSL = new PersonDSL()
        personDSL.execute()
    }


}


class Name {
    String firstname
    String lastname

    String toString() {
        "$firstname.$lastname"
    }
}

class Action {
    String eat
    String drive

    String toString() {
        "eat $eat and drive $drive"
    }
}

class Person {
    Name name
    Action action

    Person(name, action) {
        this.name = name
        this.action = action
    }

    String toString() {
        "My name is $name , I $action"
    }
}

class PersonDelegate {
    def person

    PersonDelegate(person) {
        this.person = person
    }

    /**
     * 动态的添加方法 (http://www.blogjava.net/BlueSUN/archive/2007/07/15/130318.html)
     * @param name
     * @param args
     * @return
     */
    def methodMissing(String name, Object args) {
        if ('name' == name && args[0] instanceof Closure) {
            def nameClosure = args[0]
            /*
                给nameClosure的delegate赋值，nameClosure就是name旁边的那个closure即一对大括号{}以及大括号中的内容
            */
            nameClosure.delegate = new NameDelegate(person)
            nameClosure.resolveStrategy = Closure.DELEGATE_FIRST // 指明closure中变量和方法的解析策略，本例选择DELEGATE_FIRST
            nameClosure()
        }
        if ('action' == name && args[0] instanceof Closure) {
            def actionClosure = args[0]
            actionClosure.delegate = new ActionDelegate(person)
            actionClosure.resolveStrategy = Closure.DELEGATE_FIRST
            actionClosure()
        }
    }

    def propertyMissing(String name) {}
}


class NameDelegate {
    def person

    NameDelegate(person) {
        this.person = person
    }
    /*下面这些getter和setter是为了实现下面这种赋值而写的：
    person {
        name {
            firstname = 'Groovy'
            lastname  = 'DSL'
        }
    }
    */

    def getFirstname() {
        return person.name.firstname
    }

    def setFirstname(String firstname) {
        person.name.firstname = firstname
    }

    def getLastname() {
        return person.name.lastname
    }

    def setLastname(String lastname) {
        person.name.lastname = lastname
    }

    def methodMissing(String name, Object args) {
        if ('firstname' == name) {
            person.name.firstname = args[0]
        } else if ('lastname' == name) {
            person.name.lastname = args[0]
        }
    }

    def propertyMissing(String name) {}
}


class ActionDelegate {
    def person

    ActionDelegate(person) {
        this.person = person
    }

    def getEat() {
        return person.action.eat
    }

    def setEat(String eat) {
        person.action.eat = eat
    }

    def getDrive() {
        return person.action.drive
    }

    def setDrive(String drive) {
        person.action.drive = drive
    }

    def methodMissing(String name, Object args) {
        if ('eat' == name) {
            person.action.eat = args[0]
        } else if ('drive' == name) {
            person.action.drive = args[0]
        }
    }

    def propertyMissing(String name) {}
}