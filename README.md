### 切换到第三方包的根目录

node ./../node_modules/react-native/scripts/generate-codegen-artifacts.js --path ./ --outputPath ./../node_modules/react-native-ezviz-player/generated/ -t all

### 生成android的jni部分
./gradlew generateCodegenArtifactsFromSchema
