/*
 * Copyright (c) 2017 Kotlin Algorithm Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.uuddlrlrba.ktalgs.datastructures.tree

import io.uuddlrlrba.ktalgs.datastructures.Queue
import java.util.*

class RedBlackTree<K : Comparable<K>, V> : Map<K?, V?> {
    enum class Color {RED, BLACK }

    data class Node<K, V>(
            override var key: K? = null,
            override var value: V? = null,
            var color: Color = Color.BLACK,
            var left: Node<K, V>? = null,
            var right: Node<K, V>? = null,
            var p: Node<K, V>? = null,
            var size: Int = 1) : Map.Entry<K?, V?> {

        val isLeaf: Boolean
            get() = left === null && right === null

        val isNullLeaf: Boolean
            get() = key === null && isLeaf && isBlack

        val isLeftChild: Boolean
            get() = p?.left == this

        val isRightChild: Boolean
            get() = p?.right == this

        val isRed: Boolean
            get() = color == Color.RED

        val isBlack: Boolean
            get() = color == Color.BLACK
    }

    private var root: Node<K, V>

    private val nil: Node<K, V> = Node()

    override val size: Int
        get() = size(root)

    override val entries: Set<Map.Entry<K?, V?>>
        get() {
            val set = mutableSetOf<Node<K, V>>()
            inorder(root) { set.add(it.copy()) }
            return set
        }

    override val keys: Set<K?>
        get() {
            val set = mutableSetOf<K>()
            inorder(root) { set.add(it.key!!) }
            return set
        }

    override val values: Collection<V?>
        get() {
            val queue = Queue<V>()
            inorder(root) { queue.add(it.value!!) }
            return queue
        }

    init {
        root = nil
    }

    override fun get(key: K?): V? {
        var x = root
        while (x != nil) {
            if (key!! < x.key!!) {
                x = x.left!!
            } else if (key!! > x.key!!) {
                x = x.right!!
            } else {
                return x.value
            }
        }
        return null
    }

    override fun containsKey(key: K?): Boolean {
        return get(key) != null
    }

    override fun containsValue(value: V?): Boolean {
        return any { it.value == value }
    }


    fun add(key: K, value: V) {
        var y = nil
        var x = root

        while (x != nil) {
            y = x
            if (key < x?.key!!) {
                x = x.left!!
            } else {
                x = x.right!!
            }
        }

        var z = Node(key, value, Color.RED, nil, nil, y)
        if (y == nil) {
            root = z
        } else if (z.key!! < y?.key!!) {
            y.left = z
        } else {
            y.right = z
        }
        fixup(z)
    }

    private fun fixup(z: Node<K, V>) {
        var z = z
        while (z.p!!.color == Color.RED) {
            if (z.p!!.isLeftChild) {
                var y = z.p!!.p!!.right
                if (y!!.color == Color.RED) {
                    z.p!!.color = Color.BLACK
                    y.color = Color.BLACK
                    z.p!!.p!!.color = Color.RED
                    z = z.p!!.p!!
                } else {
                    if (z.isRightChild) {
                        z = z.p!!
                        leftRotate(z)
                    }
                    z.p!!.color = Color.BLACK
                    z.p!!.p!!.color = Color.RED
                    rightRotate(z.p!!.p!!)
                }
            } else {
                var y = z.p!!.p!!.left
                if (y!!.color == Color.RED) {
                    z.p!!.color = Color.BLACK
                    y.color = Color.BLACK
                    z.p!!.p!!.color = Color.RED
                    z = z.p!!.p!!
                } else {
                    if (z.isLeftChild) {
                        z = z.p!!
                        rightRotate(z)
                    }
                    z.p!!.color = Color.BLACK
                    z.p!!.p!!.color = Color.RED
                    leftRotate(z.p!!.p!!)
                }
            }
        }
        root!!.color = Color.BLACK
    }

    private fun leftRotate(x: Node<K, V>) {
        var y = x.right
        x.right = y?.left
        if (y?.left != nil) {
            y?.left?.p = x
        }
        y?.p = x.p
        if (x.p == nil) {
            root = y!!
        } else if (x == x.p?.left) {
            x.p?.left = y
        } else {
            x.p?.right = y
        }
        y?.left = x
        x.p = y
    }

    private fun rightRotate(y: Node<K, V>) {
        var x: Node<K, V> = y.left ?: return
        y.left = x.right

        if (x.right != nil) {
            x.right?.p = y
        }
        y.p = x.p
        if (x.p == nil) {
            root = y
        } else if (x == x.p?.left) {
            x.p?.left = y
        } else {
            x.p?.right = y
        }

        x.right = y
        y.p = x
    }

    fun remove(key: K) {
        TODO()

    }

    private fun size(x: Node<K, V>): Int {
        if (x.isNullLeaf) return 0 else return x.size
    }

    fun height(): Int {
        return height(root)
    }

    private fun height(x: Node<K, V>): Int {
        if (x.isNullLeaf) return 0
        return maxOf(height(x.left!!), height(x.right!!)) + 1
    }


    override fun isEmpty(): Boolean {
        return size == 0
    }

    fun min(): K {
        return min(root).key!!
    }

    fun min(node: Node<K, V>): Node<K, V> {
        if (node.isNullLeaf) throw NoSuchElementException()
        var x: Node<K, V> = node
        while (!x.left!!.isNullLeaf) {
            x = x.left!!
        }
        return x
    }

    fun max(): K {
        return max(root).key!!
    }

    fun max(node: Node<K, V>): Node<K, V> {
        if (node.isNullLeaf) throw NoSuchElementException()
        var x: Node<K, V> = node
        while (!x.right!!.isNullLeaf) {
            x = x.right!!
        }
        return x
    }

    fun pollMin() {
        if (root.isNullLeaf) throw NoSuchElementException()
        root = pollMin(root)
    }

    private fun pollMin(x: Node<K, V>): Node<K, V> {
        if (x.left!!.isNullLeaf) return x.right!!
        x.left = pollMin(x.left!!)
        x.size = size(x.left!!) + size(x.right!!) + 1
        return x
    }

    fun pollMax() {
        if (root.isNullLeaf) throw NoSuchElementException()
        root = pollMax(root)
    }

    private fun pollMax(x: Node<K, V>): Node<K, V> {
        if (x.right!!.isNullLeaf) return x.left!!
        x.right = pollMax(x.right!!)
        x.size = size(x.left!!) + size(x.right!!) + 1
        return x
    }

    private fun inorder(x: Node<K, V>?, lambda: (Node<K, V>) -> (Unit)) {
        if (x == null) return
        inorder(x.left, lambda)
        lambda(x)
        inorder(x.right, lambda)
    }

    fun verify(): Boolean {
        if (root.isNullLeaf) {
            return true
        }
        return property2() && property4() && property5()
    }

    // Property 1: Every node is either red or black -> fullfilled through setting node.color of type
    // RedBlackTree.Color

    // Property 2: The root is black
    private fun property2(): Boolean {
        if (root.isRed) {
            return false
        }
        return true
    }

    // Property 3: Every nullLeaf is black -> fullfilled through initialising nullLeafs with color = black

    // Property 4: If a node is red, then both its children are black
    private fun property4(): Boolean {
        return property4(root)
    }

    private fun property4(node: Node<K, V>): Boolean {
        if (node === nil) return true
        val leftChild = node.left!!
        val rightChild = node.right!!
        if (node.isRed) {
            if (!leftChild.isNullLeaf && leftChild.isRed) return false
            if (!rightChild.isNullLeaf && rightChild.isRed) return false
        }
        return property4(leftChild) && property4(rightChild)
    }

    // Property 5: For each node, all paths from the node to descendant leaves contain the same number
    // of black nodes (same blackheight)
    private fun property5(): Boolean {
        return property5(root) != -1
    }

    private fun property5(node: Node<K, V>): Int {
        if (node.isNullLeaf) return 0
        val left = property5(node.left!!)
        val right = property5(node.right!!)

        if (left == -1 || right == -1) {
            return -1
        } else if (left == right) {
            val addedHeight = if (node.isBlack) 1 else 0
            return left + addedHeight
        } else {
            return -1
        }
    }

}
