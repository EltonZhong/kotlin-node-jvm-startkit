# Delicate

- Kotlin multiplatform startkit, including js(both node.js and frontend js are OK) and jvm. (It's scalable!)
- Ktor support, okhttp for jvm, and window.fetch for js.(will support node in next release)
- Corotines examples in multiplatform project.

# Kotlin multiplatform
Kotlin multiplatform`(Below we call it kotlinmp for short)` is a feature released in Kotlin1.2. Kotlinmp makes you able to `write once, compile everywhere`:
> You can write same kotlin code and use it in Jvm(of course), Javascript(`Both browser and node.js`), Native project(`Android, iOS, linux...etc`)

## Quick start

Setting up is not difficult. Follow the link below and you can easily start your own mp project.
[Start Kotlin multiplatform with gradle](https://www.kotlincn.net/docs/reference/building-mpp-with-gradle.html#setting-up-a-multiplatform-project)


Or if you want to get a really quick start for node.js/Jvm project, below are what you'll get.

run shell script below to quick setup:
```sh
# Get startkit project
git clone https://github.com/EltonZhong/kotlin-node-jvm-startkit.git
# Generate .js files and .class files
cd kotlin-node-jvm-startkit && gradle build && gradle node
# Run your program(if you got a main function in it).
cd node && npm start
```

## Detail Configurations
You might find that official documents don't give you an example to config to produce a runnable js program. Yes, it's reasonable. You can choose to compile js to commonjs used in node, or plainjs used in the browser.
run `kotlinc-js -h` to get detail.

So when we are using node.js, we should config like below:

### gradle preset config.
```
// The 'js' parameter can be any name
fromPreset(presets.js, 'js', {
    configure(compilations.main) {
        tasks.getByName(compileKotlinTaskName).kotlinOptions {
            // You can config them to false on production env
            metaInfo = true
            sourceMap = true
            sourceMapEmbedSources = "always"

            moduleKind = 'commonjs'

            // The program will be bundled to a single js file.
            outputFile = "node/kotlin/main/index.js"
            // Whether we should call kotlin main func directly in js.
            main = "call"
        }
    }

    configure(compilations.test) {
        tasks.getByName(compileKotlinTaskName).kotlinOptions {
            metaInfo = true
            sourceMap = true
            moduleKind = 'commonjs'
            sourceMapEmbedSources = "always"
            outputFile = "node/kotlin/test/index.js"
        }
    }
})

```
run `gradle build`, and we can get the produced file at node/kotlin/main/index.js
### gradle task config
Get produced index.js is not enough. When we run node index.js, we find that we are lacking a lot of kotlin.js dependencies, like below:
> Error loading module 'kotlin'

So what should we do? As we all know, we should have all the .js files as dependencies. Should we install them like `npm install kotlin`?
***We should never do it***

Kotlin community is not aware of that they should publish their .js package to npm registry. So It's terrible: when you want to publish your kotlin.js program to npm, you can't, because the packages you depend on is NOT in npm registry.

So how do we solve this?
There you go.

> When there is a will, there is a way.
>> When you put you .js file into node_modules, you can require it magically.
```sh
touch node_modules/hellokotlin.js
```

>> Write js code below, and you can find it working:
 ```js
require('hellokotlin')
```

This is not officially supported, but it really works.

>You might think that you can install the js file through npm install, well you can't. See[doc](https://docs.npmjs.com/cli/install). Well, talk about it in the next section.


So, given the hack way above, we can write a gradle task to move all the js dependencies to node_modules like below:
```grovvy
task node(dependsOn: [jsJar, jsTestClasses]) {
    doLast {
        copy {
            def jsCompilations = kotlin.targets.js.compilations
            from jsCompilations.main.output
            from kotlin.sourceSets.jsMain.resources.srcDirs
            dump(kotlin.sourceSets.jsMain)
            dump(jsCompilations.main)
            jsCompilations.main.runtimeDependencyFiles.each {
                if (it.exists() && !it.isDirectory()) {
                    from zipTree(it.absolutePath).matching { include '*.js' }
                }
            }
            into "node/node_modules"
        }
    }
}
```
Also, you can output it into another dir like node/kotlin_dependencies, and specify node path to make program find files in other dirs. 

```sh
`export NODE_PATH="$NODE_PATH:`pwd`/kotlin-dependencies"`
```

## Publish your package!
When you want to publish your kotlin-js program, you can either publish the kotlin library to maven repository or publish the generated kotlin.js file. The previous is just the same as publishing other packages to maven repo, but when publishing to npm registry, we should do something more.

[Click here](https://therollingstones.cn/2018/12/18/code/kotlin/Publish/) To see how to publish a kotlin-js package to npm registry.

## Documents
[Kotlin official documents ](https://www.kotlincn.net/docs/reference/multiplatform.html)

[Start Kotlin multiplatform with gradle](https://www.kotlincn.net/docs/reference/building-mpp-with-gradle.html#setting-up-a-multiplatform-project)

[How to run Js tests in kotlin](https://blog.kotlin-academy.com/kotlin-js-configuration-made-simple-ef0e361fcd4)