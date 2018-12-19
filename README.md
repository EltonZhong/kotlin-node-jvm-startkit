# Kotlin-node-jvm-startkit

- A example for how to publish your kotlin-js app written in kotlin to npm registry.
- Kotlin multiplatform startkit, including js(both node.js and frontend js are OK) and jvm.


## Usage

### Js
- Run test written in kotlin: cd node && npm test
- Run main function written in kotlin: cd node && npm start
- Publish to npm registry: modify package info in `node/package.json`, and run `npm publish`

### Jvm
- Test: gradle jvmTest

> This project is based on, and can be full explained in this document:  https://therollingstones.cn/2018/12/18/code/kotlin/KotlinMultiPlatformSetup/