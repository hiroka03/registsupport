# SE20G1 (システムプログラミングのテンプレートリポジトリ)

## 実行方法
SE20G1上で以下のコマンドを実行し、表示されたURLにアクセスする<br>
`docker-compose -f docker/local/docker-compose.yml up`


## ディレクトリ構成

<pre>
.
├── README.md
├── build.gradle
├── docker
│   ├── README.md
│   ├── local
│   │   ├── docker-compose.yml
│   │   ├── java
│   │   ├── mysql
│   │   └── wait-for-it.sh
│   └── production
│       ├── docker-compose.yml
│       ├── java
│       └── mysql
├── docs
│   └── README.md
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└──src
    ├── main
    │   ├── java
    │   └── webapp
    └── test
        └── java
</pre>
