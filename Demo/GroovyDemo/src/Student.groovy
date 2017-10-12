/**
 * Created by zhengchen on 12/10/2017.
 */
class Student {
    protected dynamicProps = [:]

    void setProperty(String pName, val) {
        dynamicProps[pName] = val
    }

    def getProperty(String pName) {
        dynamicProps[pName]
    }

    /*      def invokeMethod(String name, Object args) {
              return "called invokeMethod $name $args"
          }*/

    def methodMissing(String name, args) {
        return "unknown method $name(${args.join(',')})"
    }
}
