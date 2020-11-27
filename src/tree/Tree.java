package tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
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


    /**
     * #94 二叉树的中序遍历
     * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     */
    // 递归方式
    public List<Integer> inorderTraversal_recursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder_recursive(root, result);
        return result;
    }
    private void inorder_recursive(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder_recursive(root.left, res);
        res.add(root.val);
        inorder_recursive(root.right, res);
    }
    // 栈实现的非递归方式
    public List<Integer> inorderTraversal_stack(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    // leetcode官方给的第三种实现 mirros
    //如果 xx 无左孩子，先将 xx 的值加入答案数组，再访问 xx 的右孩子，即 x = x.\textit{right}x=x.right。
    //如果 xx 有左孩子，则找到 xx 左子树上最右的节点（即左子树中序遍历的最后一个节点，xx 在中序遍历中的前驱节点），
    //     我们记为 \textit{predecessor}predecessor。根据 \textit{predecessor}predecessor 的右孩子是否为空，进行如下操作。
    //如果 \textit{predecessor}predecessor 的右孩子为空，则将其右孩子指向 xx，然后访问 xx 的左孩子，即 x = x.\textit{left}x=x.left。
    //如果 \textit{predecessor}predecessor 的右孩子不为空，则此时其右孩子指向 xx，说明我们已经遍历完 xx 的左子树，我们将
    //     \textit{predecessor}predecessor 的右孩子置空，将 xx 的值加入答案数组，然后访问 xx 的右孩子，即 x = x.\textit{right}x=x.right。
    //重复上述操作，直至访问完整棵树。
    public List<Integer> inorder_mirros(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode predecessor = null;
        while (root != null) {
            if (root.left != null) {
                // predecessor 是当前root节点向左一步，此时去找最右节点
                predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }

                // predecessor 右指针指向root 继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                } else {
                    // 左子树访问完了，断开链接
                    res.add(root.val);
                    predecessor.right = null;
                    root = root.right;
                }
            } else {
                res.add(root.val);
                root = root.right;
            }
        }
        return res;
    }

    /**
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     *
     * 说明: 叶子节点是指没有子节点的节点。
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (null == root) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

}
