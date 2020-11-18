package tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/18 4:13 下午
 * info : 树相关的题目
 */
public class Tree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * # 226 翻转一个二叉树 (每个左右节点互换）
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.right);
        TreeNode right = invertTree(root.left);

        root.left = right;
        root.right = left;
        return root;
    }

    /** #104 找出二叉树的最大深度
     *二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 方法1 递归 方法2 广度优先
     */
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        int res = 1;
        int leftDep = res + maxDepth(root.left);
        int rightDep = res + maxDepth(root.right);

        return Math.max(leftDep, rightDep);
    }

    public int maxTreeDepth_BFS(TreeNode root) {
        // 依次入队 并按照深度++
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode treeNode = queue.poll();
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
                size--;
            }
            ans++;
        }
        return ans;
    }

}
