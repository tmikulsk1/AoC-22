package day07

import readInput

data class Node(
    val key: String,
    val level: Int,
    val size: Int = 0,
    var totalSize: Int = 0,
    var children: List<Node>? = null,
    val parent: String? = null
)

var globalCurrentDir = ""
var globalParentDir = ""
var globalLevel = 1
var globalRootNode = Node(key = "dir /", 1)


var currentNodeParent = ""
var currentNodeKey = ""

fun test() {
    val file = readInput("day07/input.txt").readLines()

    val rootNode = globalRootNode

    var goToDir = ""
    var skipCharValidation = false


    val nodeList = mutableListOf<Node>()
    var currentDir = ""
    var previsousDir = ""
    var parentNode = rootNode
    var level = 1

    file.forEachIndexed { index, line ->
        println(line)
        if (!skipCharValidation) {
            if (line.contains("cd") && line.contains("..").not()) {
                level++
                globalLevel = level
            }
            nodeList.clear()
            if (line.contains("$ cd")) {
                val lastChar = line.toList().last()
                if (lastChar.equals('/', true)) {
                    currentDir = "dir /"
                    globalCurrentDir = currentDir
                    parentNode = rootNode

                    return@forEachIndexed
                } else if (lastChar.equals('.', true)) {
                    level--
                    parentNode = rootNode.getPreviousNode(level)
                    return@forEachIndexed
                } else {
                    // cd x

                    previsousDir = currentDir
                    globalParentDir = previsousDir
                    currentDir = line.replace("$ cd ", "dir ")
                    globalCurrentDir = currentDir


//                    parentNode = rootNode.getPreviousNode(level)

//                    currentNodeKey = rootNode.key
//                    currentNodeParent = rootNode.parent.orEmpty()

                    val currentNode = rootNode.findNodeByCurrentKeyAndLevel(currentDir, level -1)
                    val parent = rootNode.findNodeByCurrentKeyAndLevel(currentNode.parent.orEmpty(), currentNode.level -1)
                    parentNode = currentNode

//                    currentNodeParent = parent.key.orEmpty()
//                    currentNodeKey = currentNode.key

                    return@forEachIndexed
                }
            }
            if (line.contains("$ ls") && file[index + 1].contains("$").not()) {
                skipCharValidation = true
                return@forEachIndexed
            }
        }

        // createNodes
        if (index < file.size - 1) {
            if (file[index + 1].contains("$")) {
                skipCharValidation = false
            }
        }

        val (size, fileName) = if (line.contains("dir")) {
            listOf("0", line)
        } else {
            line.split(" ")
        }

        // convert size from string to int
        val intSize = size.toInt()
        val node = parentNode.generateNode(fileName, intSize, level) ?: return@forEachIndexed

        nodeList.add(node)
        parentNode.children = nodeList.toList()
    }

    println(rootNode)

    globalRootNode = rootNode

    globalRootNode.sumTotalForEachParent()
    println(globalRootNode)
    globalRootNode.sumNodesLimit100000()
    println(sum)
}

fun Node.generateNode(key: String, size: Int = 0, level: Int): Node {
    return Node(
        key,
        level,
        size,
        parent = this.key
    )
}

fun Node.findParentNode(key: String): Node {
    var foundChild: Node = this
    with(this) {
        while (this.children != null) {
            this.children!!.forEachIndexed { index, child ->

                if (child.key == key) {
                    foundChild = this.children!![index]
                    return@with
                }
            }
        }
    }
    return foundChild
}

fun Node.findNodeByCurrentKeyAndLevel(keyToFind: String, levelToMatch: Int): Node {

    if (isThisNodeFound(keyToFind, levelToMatch)) {
        return this
    }

    this.children!!.getNodeWithKeyAndLevel(keyToFind, levelToMatch)

    return currentNodeFound
}

var currentNodeFound: Node = globalRootNode

fun List<Node>.getNodeWithKeyAndLevel(keyToFind: String, levelToMatch: Int) {
    with(this) {
        forEachIndexed { index, child ->
            if (child.isThisNodeFound(keyToFind, levelToMatch)) {
                currentNodeFound = this[index]
                return@with
            }

            if (child.children?.isNotEmpty() == true) {
                child.children?.getNodeWithKeyAndLevel(keyToFind, levelToMatch)
            }
        }
    }
}

fun Node.isThisNodeFound(keyToFind: String, levelToMatch: Int) =
    this.key.equals(keyToFind, true) && this.level == levelToMatch

// tem que ser a root
fun Node.getPreviousNode(level: Int): Node {
    var node = this

//    if (isThisNode(globalParentDir, globalCurrentDir, level)) {
    if (isThisNode(currentNodeParent, currentNodeKey, level)) {
        return node
    }
//    val fnode = this.children!!.getNode(globalParentDir, globalCurrentDir, level)
    val fnode = this.children!!.getNode(currentNodeParent, currentNodeKey, level)
    if (fnode != null) node = fnode

//    globalParentDir = node.parent.orEmpty()
//    globalCurrentDir = node.key

    currentNodeKey = node.key
    currentNodeParent = node.parent.orEmpty()

    return node
}

fun List<Node>.getNode(parentKey: String, childKey: String, level: Int): Node? {
    var node: Node? = null

    with(this) {
        forEachIndexed { index, child ->
            if (child.isThisNode(parentKey, childKey, level)) {
                node = this[index]
                return@with
            }

            if (child.children?.isNotEmpty() == true) {
                child.children?.getNode(globalParentDir, globalCurrentDir, level)
            }
        }
    }

    return node
}

fun Node.isThisNode(previousKey: String, currentDir: String, level: Int): Boolean {
    return this.key.equals(previousKey, true) &&
            this.children?.any { it.level == level } == true &&
            this.children?.any { it.key == currentDir } == true
}

fun main() {
    test()
    getaaa()
}


// rootNode
fun Node.sumTotalForEachParent() {
    this.getAllChildrenSum()
}

fun Node.getAllChildrenSum() {
    val total = loopSumChildren()

    totalSize += total

    children?.forEach { subChildren ->
        subChildren.getAllChildrenSum()
    }
}

fun Node.loopSumChildren(): Int {
    return children?.sumOf { child ->
        child.size + child.loopSumChildren()
    } ?: 0
}

var sum = 0

fun Node.sumNodesLimit100000() {
    if (totalSize <= 100000) {
        sum += totalSize
    }

    children?.forEach { child ->
        child.sumNodesLimit100000()
    }
}

val flatNodes = mutableListOf<Node>()

fun Node.getFlatListOfNodesDirWithTotalSize() {

    if (this.key.contains("dir ")) {
        flatNodes.add(this)
    }

    this.children?.forEach { child ->
        child.getFlatListOfNodesDirWithTotalSize()
    }
}

fun getaaa() {
    val valueToRemove = 70000000 - globalRootNode.totalSize

    globalRootNode.getFlatListOfNodesDirWithTotalSize()

    val minValue = flatNodes.filterNot { it.key == "dir /" }.filter {
        it.totalSize + valueToRemove >= 30000000
    }.minBy { it.totalSize }

    println("remove: " + valueToRemove)
    println(minValue)
}
