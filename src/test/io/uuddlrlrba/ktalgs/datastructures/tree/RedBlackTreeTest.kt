package io.uuddlrlrba.ktalgs.datastructures.tree

import org.junit.Assert
import org.junit.Test

class RedBlackTreeTest{

    @Test
    fun empty() {
        val tree = RedBlackTree<Int, Int>()
        Assert.assertEquals(0, tree.size)
        Assert.assertTrue(tree.isEmpty())
    }

    @Test
    fun sizeOfOne() {
        val tree = RedBlackTree<Int, String>()
        tree.add(1, "1")
        Assert.assertFalse(tree.isEmpty())
        Assert.assertEquals(1, tree.size)
        Assert.assertEquals(1, tree.height())
        Assert.assertEquals(1, tree.min())
        Assert.assertEquals(1, tree.max())
        Assert.assertEquals("1", tree[1])
        tree.pollMin()
        Assert.assertTrue(tree.isEmpty())
    }

    @Test
    fun sizeOfThree() {
        val tree = BinarySearchTree<Int, String>()
        tree.add(1, "1")
        tree.add(2, "2")
        tree.add(3, "3")
        Assert.assertFalse(tree.isEmpty())
        Assert.assertEquals(3, tree.size)
        Assert.assertEquals(3, tree.height())
        Assert.assertEquals(1, tree.min())
        Assert.assertEquals(3, tree.max())
        Assert.assertEquals("1", tree[1])
        Assert.assertEquals("2", tree[2])
        Assert.assertEquals("3", tree[3])
        tree.pollMin()
        Assert.assertEquals(2, tree.min())
        Assert.assertEquals(3, tree.max())
        Assert.assertEquals("2", tree[2])
        Assert.assertEquals("3", tree[3])
        tree.pollMax()
        Assert.assertEquals(2, tree.min())
        Assert.assertEquals(2, tree.max())
        Assert.assertEquals("2", tree[2])
    }

    @Test
    fun overwrite() {
        val tree = BinarySearchTree<Int, String>()
        tree.add(1, "1")
        Assert.assertFalse(tree.isEmpty())
        Assert.assertEquals(1, tree.size)
        Assert.assertEquals(1, tree.height())
        Assert.assertEquals("1", tree[1])
        tree.add(1, "2")
        Assert.assertFalse(tree.isEmpty())
        Assert.assertEquals(1, tree.size)
        Assert.assertEquals(1, tree.height())
        Assert.assertEquals(1, tree.min())
        Assert.assertEquals(1, tree.max())
        Assert.assertEquals("2", tree[1])
        tree.pollMin()
        Assert.assertTrue(tree.isEmpty())
    }

    @Test
    fun letters() {
        val tree = BinarySearchTree<Char, String>()
        val letters = arrayOf('j', 'p', 'q', 's', 'f', 'o', 'g', 'v', 'h', 'm', 'x', 'z',
                'l', 'n', 'd', 'c', 'a', 'r', 'b', 't', 'i', 'u', 'w', 'k', 'y', 'e')
        letters.forEach { tree.add(it, it.toString()) }

        Assert.assertEquals(letters.toSet(), tree.keys)
        Assert.assertArrayEquals(letters.map { it.toString() }.sorted().toTypedArray(),
                tree.values.sorted().toTypedArray())

        Assert.assertEquals(26, tree.size)
        Assert.assertEquals('a', tree.min())
        Assert.assertEquals('z', tree.max())
        tree.pollMin()
        Assert.assertEquals(25, tree.size)
        Assert.assertEquals('b', tree.min())
        Assert.assertEquals('z', tree.max())
        tree.pollMax()
        Assert.assertEquals(24, tree.size)
        Assert.assertEquals('b', tree.min())
        Assert.assertEquals('y', tree.max())
        tree.pollMin()
        Assert.assertEquals(23, tree.size)
        Assert.assertEquals('c', tree.min())
        Assert.assertEquals('y', tree.max())
        tree.pollMax()
        Assert.assertEquals(22, tree.size)
        Assert.assertEquals('c', tree.min())
        Assert.assertEquals('x', tree.max())
        tree.pollMin()
        Assert.assertEquals(21, tree.size)
        Assert.assertEquals('d', tree.min())
        Assert.assertEquals('x', tree.max())
        tree.pollMax()
        Assert.assertEquals(20, tree.size)
        Assert.assertEquals('d', tree.min())
        Assert.assertEquals('w', tree.max())
        tree.pollMin()
        tree.pollMin()
        tree.pollMin()
        Assert.assertEquals(17, tree.size)
        Assert.assertEquals('g', tree.min())
        Assert.assertEquals('w', tree.max())
        tree.pollMax()
        tree.pollMax()
        tree.pollMax()
        Assert.assertEquals(14, tree.size)
        Assert.assertEquals('g', tree.min())
        Assert.assertEquals('t', tree.max())
        tree.pollMin()
        tree.pollMin()
        tree.pollMin()
        tree.pollMin()
        tree.pollMin()
        Assert.assertEquals(9, tree.size)
        Assert.assertEquals('l', tree.min())
        Assert.assertEquals('t', tree.max())
        tree.pollMax()
        tree.pollMax()
        tree.pollMax()
        tree.pollMax()
        tree.pollMax()
        Assert.assertEquals(4, tree.size)
        Assert.assertEquals('l', tree.min())
        Assert.assertEquals('o', tree.max())
        tree.pollMin()
        Assert.assertEquals(3, tree.size)
        Assert.assertEquals('m', tree.min())
        Assert.assertEquals('o', tree.max())
        tree.pollMax()
        Assert.assertEquals(2, tree.size)
        Assert.assertEquals('m', tree.min())
        Assert.assertEquals('n', tree.max())
        tree.pollMin()
        Assert.assertEquals(1, tree.size)
        Assert.assertEquals('n', tree.min())
        Assert.assertEquals('n', tree.max())
        tree.pollMin()
        Assert.assertTrue(tree.isEmpty())
    }

}