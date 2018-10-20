IDE：
    Intellij IDEA
项目构建管理
    Mavens
思路：
    使用apache第三方jar包poi读取Excel文件，手动拼写成所需格式输出
调用层次：
    MainApp.java 主方法
        createX.java 将读取数据拼成所需格式
            ExcelReader.java 读取Excel文件成为java可编辑类型
            TxtReader.java 读取TXT文件