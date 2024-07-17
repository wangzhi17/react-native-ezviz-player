# React Native 新架构集成萤石云SDK，实现预览和对讲功能，支持react native 0.74及以上
# The new React Native architecture integrates EZVIZ Cloud SDK to implement preview and intercom functions, supporting react native 0.74 and above.

### 切换到第三方包的根目录或者使用yarn android、yarn iOS自动生成缺失的文件
### Switch to the root directory of the third-party package or use yarn android or yarn iOS to automatically generate missing files

node ./../node_modules/react-native/scripts/generate-codegen-artifacts.js --path ./ --outputPath ./../node_modules/react-native-ezviz-player/generated/ -t all

### 生成android的jni部分
cd android && ./gradlew generateCodegenArtifactsFromSchema
