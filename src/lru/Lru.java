package lru;

import org.w3c.dom.Node;

import java.util.HashMap;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/19 1:40 下午
 * info :
 */
public class Lru {

    /**
     * 粉笔3面面试题，手写一个LRU cache
     * 当时脑子比较混乱 写的基于hashmap实现  linkedlist应该更简单
     * 保证维护最近的节点即可
     */

    class Node {
        private String val;
        private String key;
        private Node pre;
        private Node next;
    }

    private Node end;
    private Node head;
    private int limit;
    private HashMap<String, Node> hashMap;

    public String get(String key) {
        Node node = hashMap.get(key);
        if (null == node) {
            return null;
        }
        refresh(node);
        return node.val;
    }

    public synchronized void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            if (hashMap.size() >= limit) {
                String oldestKey = removeNode(node);
                hashMap.remove(oldestKey);
            }

            node = new Node();
            addNode(node);
            hashMap.put(key, node);
        }
    }

    /**
     * 更新节点
     * @param node
     */
    public synchronized void refresh(Node node) {
        if (node == end) {
            return;
        }
        removeNode(node);
        addNode(node);
    }

    /**
     * 尾部插入节点
     * @param node
     */
    public synchronized void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    /**
     * 移除节点
     * @param node
     * @return
     */
    public synchronized String removeNode(Node node) {
        if (node == end) {
            end = end.pre;
        } else if (node == head) {
            head = head.next;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }
}
