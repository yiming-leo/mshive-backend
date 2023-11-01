package com.yimingliao.mshivebackend.utils;

import com.yimingliao.mshivebackend.vo.BookmarkTreeVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/31 23:00
 */
@Component
public class BookmarkTreeUtil {

    // 保存参与构建树形的所有数据（通常数据库查询结果）
    public List<BookmarkTreeVO> nodeList = new ArrayList<>();

    public BookmarkTreeUtil(List<BookmarkTreeVO> nodeList){
        this.nodeList = nodeList;
    }

    //入口：获取需构建的所有根节点（顶级节点） "0"
    public List<BookmarkTreeVO> getRootNode(){
        // 保存所有根节点（所有根节点的数据）
        List<BookmarkTreeVO> rootNodeList = new ArrayList<>();
        // bookmarkTreeVO：查询出的每一条数据（节点）
        for (BookmarkTreeVO bookmarkTreeVO : nodeList){
            // 判断当前节点是否为根节点，此处注意：若parentId类型是String，则要采用equals()方法判断。
            if ("".equals(bookmarkTreeVO.getParentId())){
                // 是，添加
                rootNodeList.add(bookmarkTreeVO);
            }
        }
        return rootNodeList;
    }

    //根据每一个顶级节点（根节点）进行构建树形结构
    public List<BookmarkTreeVO> buildTree(){
        // treeNodes：保存一个顶级节点所构建出来的完整树形
        List<BookmarkTreeVO> treeNodes = new ArrayList<>();
        // getRootNode()：获取所有的根节点
        for (BookmarkTreeVO treeRootNode : getRootNode()) {
            // 将顶级节点进行构建子树
            treeRootNode = buildChildTree(treeRootNode);
            // 完成一个顶级节点所构建的树形，增加进来
            treeNodes.add(treeRootNode);
        }
        return treeNodes;
    }

    //递归-----构建子树形结构，pNode 根节点（顶级节点），return 整棵树
    public BookmarkTreeVO buildChildTree(BookmarkTreeVO pNode){
        List<BookmarkTreeVO> childTree = new ArrayList<>();
        // nodeList：所有节点集合（所有数据）
        for (BookmarkTreeVO treeNode : nodeList) {
            // 判断当前节点的父节点ID是否等于根节点的ID，即当前节点为其下的子节点
            if (treeNode.getParentId().equals(pNode.getId())) {
                // 再递归进行判断当前节点的情况，调用自身方法
                childTree.add(buildChildTree(treeNode));
            }
        }
        // for循环结束，即节点下没有任何节点，树形构建结束，设置树结果
        pNode.setChildren(childTree);
        return pNode;
    }
}
