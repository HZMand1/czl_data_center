package cn.paohe.sys.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.sys.MenuButtonEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.exp.SeedException;
import cn.paohe.sys.dao.MenuButtonMapper;
import cn.paohe.sys.service.IMenuButtonService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.ErrorMessageUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * TODO  菜单按钮实现类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年10月22日 上午9:06:53
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Transactional(rollbackFor = Exception.class)
@Service("menuInfoService")
public class MenuButtonServiceImpl implements IMenuButtonService {

    private final static Logger log = LoggerFactory.getLogger(MenuButtonServiceImpl.class);

    @Resource
    private MenuButtonMapper menuButtonMapper;

    /**
     * TODO 获取菜单按钮列表
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findMenuAllList(MenuButtonEntity menu) {
        try {
            List<MenuButtonEntity> configList = menuButtonMapper.select(menu);
            AjaxResult result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取菜单按钮列表");
            result.setData(configList);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->findMenuList]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->findMenuList]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据条件查询菜单按钮分页
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findMenuAllPage(MenuButtonEntity menu) {
        try {
            PageHelper.startPage(menu.getStart(), menu.getPageSize());
            List<MenuButtonEntity> list = menuButtonMapper.select(menu);
            PageInfo<MenuButtonEntity> pageInfo = new PageInfo<MenuButtonEntity>(list);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "根据条件查询菜单按钮分页", pageInfo);
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->findMenuPage]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->findMenuPage]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据Id获取菜单按钮
     *
     * @param id
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:09:06
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findMenuById(Long id) {
        try {
            if (ObjectUtils.isNullObj(id)) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "Id不能为空");
            }
            AjaxResult result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "根据Id获取菜单按钮");
            MenuButtonEntity menuEnity = null;
            menuEnity = menuButtonMapper.selectByPrimaryKey(id);
            if (menuEnity == null) {
                return result;
            }
            if (StringUtil.isNotEmpty(menuEnity.getParentId())) {
                //存在父节点的话这查询父菜单信息
                MenuButtonEntity vo = menuButtonMapper.selectByPrimaryKey(menuEnity.getParentId());
                menuEnity.setParent(vo);
            }
            result.setData(menuEnity);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->findMenuById]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->findMenuById]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 新增菜单按钮
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult insertMenu(MenuButtonEntity menu) {
        try {
            //获取主键ID
            AjaxResult result = null;
            if (StringUtil.isBlank(menu.getName())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "菜单按钮名称不能为空", menu);
            }
            menu.setAddTime(new Date());
            /*是否存在菜单按钮编码*/
            if (menu.getType() == null) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "菜单类型不能为空", menu);
            }
            if (ObjectUtils.isNullObj(menu.getOprUserId())) {
                menu.setOprUserId(UserUtil.getUserEntity().getUserId());
                menu.setOprTime(new Date());
            }
            if (ObjectUtils.isNullObj(menu.getAddUserId())) {
                menu.setAddUserId(UserUtil.getUserEntity().getUserId());
                menu.setAddTime(new Date());
            }
            if (ObjectUtils.isNullObj(menu.getAliveFlag())) {
                menu.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            }
            int i = menuButtonMapper.insert(menu);
            if (i > 0) {
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功");
                result.setData(menu);
                return result;
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败", menu);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->insertMenu]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->insertMenu]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改菜单按钮
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:05
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult updateMenu(MenuButtonEntity menu) {
        try {
            if (ObjectUtils.isNullObj(menu.getMenuId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "菜单id不能为空", menu);
            }
            AjaxResult result = null;
            menu.setOprTime(new Date());
            menu.setOprUserId(UserUtil.getUserEntity().getUserId());
            int i = menuButtonMapper.updateByPrimaryKeySelective(menu);
            if (i > 0) {
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改成功", menu);
                result.setData(menu);
                return result;
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改失败");
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->updateMenu]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->updateMenu]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改菜单状态
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:15
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult updateMenuEnable(MenuButtonEntity menu) {
        try {
            AjaxResult result = null;
            if (ObjectUtils.isNullObj(menu.getMenuId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "菜单id不能为空", menu);
            }
            if (ObjectUtils.isNullObj(menu.getAliveFlag())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "状态不能为空", menu);
            }
            //不能禁用菜单管理
            if (StringUtil.equals(menu.getMenuId(),1)) {
                ErrorMessageUtils.setErrorMessage("不能禁用菜单管理");
            }
            menu.setOprUserId(UserUtil.getUserEntity().getUserId());
            //先修改当前节点状态
            updateEnable(menu, menu.getAliveFlag());
            MenuButtonEntity entity = new MenuButtonEntity();
            entity.setMenuId(menu.getMenuId());
            entity.setAliveFlag(-1);
            List<MenuButtonEntity> menuList = getMenuNodes(entity);
            if (CollectionUtil.isNotEmpty(menuList)) {
                /*获取子孙节点*/
                continueGetNodesZtree(menuList);
                for (MenuButtonEntity menuVo : menuList) {
                    updateEnable(menuVo, menu.getAliveFlag());
                }
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", menu);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[MenuButtonServiceImpl->deleteMenu]" + e);
            throw new SeedException("报错-位置：[MenuButtonServiceImpl->deleteMenu]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改状态
     *
     * @param menu
     * @param enable void
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 下午3:54:00
     */
    private void updateEnable(MenuButtonEntity menu, Integer enable) {
        menu.setOprUserId(UserUtil.getUserEntity().getUserId());
        menu.setOprTime(new Date());
        menu.setAliveFlag(enable);
        menuButtonMapper.updateByPrimaryKeySelective(menu);
        //递归修改所有子节点状态
        List<MenuButtonEntity> childrenList = menu.getChildren();
        if (childrenList != null && childrenList.size() > 0) {
            for (MenuButtonEntity entity : childrenList) {
                updateEnable(entity, enable);
            }
        }
    }

    /**
     * TODO 获取当前节点下的所有子节点
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 下午2:24:12
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findMenuNodes(MenuButtonEntity menu) {
        if (ObjectUtils.isNullObj(menu.getMenuId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "菜单id不能为空");
        }
        List<MenuButtonEntity> menuList = getMenuNodes(menu);
        if (CollectionUtil.isEmpty(menuList)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取当前节点下的所有子节点", menuList);
        }
        continueGetNodes(menuList);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取当前节点下的所有子节点", menuList);
    }

    /**
     * TODO 获取菜单树结构
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月28日 上午11:16:29
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findMenuZtree(MenuButtonEntity menu) {
        Condition cond = new Condition(MenuButtonEntity.class);
        Criteria criteria = cond.or();
        criteria.andEqualTo(MenuButtonEntity.key.addUserId.toString(),UserUtil.getUserEntity().getUserId());
        criteria.andEqualTo(MenuButtonEntity.key.parentId.toString(),0);
        List<MenuButtonEntity> menuList = menuButtonMapper.selectByCondition(cond);
        if (CollectionUtil.isEmpty(menuList)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取菜单树结构", menuList);
        }
        continueGetNodesZtree(menuList);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取菜单树结构", menuList);
    }

    /**
     * TODO 获取菜单树结构
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月28日 上午11:16:29
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findEnableMenuZtree(MenuButtonEntity menu) {
        Condition cond = new Condition(MenuButtonEntity.class);
        Criteria criteria = cond.or();
        criteria.andEqualTo(MenuButtonEntity.key.addUserId.toString(),UserUtil.getUserEntity().getUserId());
        criteria.andEqualTo(MenuButtonEntity.key.parentId.toString(),0);
        criteria.andEqualTo(MenuButtonEntity.key.aliveFlag.toString(), DataCenterCollections.YesOrNo.YES.value);
        List<MenuButtonEntity> menuList = menuButtonMapper.selectByCondition(cond);
        if (CollectionUtil.isEmpty(menuList)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取菜单树结构", menuList);
        }
        continueGetNodesZtreeEnabl(menuList);
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取菜单树结构", menuList);
    }

    /**
     * TODO 删除菜单（物理删除）
     *
     * @param menu
     * @return
     * @throws
     * @author 黄芝民
     * @date 2020/12/4 9:35
     */
    @Override
    @Transactional
    public AjaxResult delMenu(MenuButtonEntity menu) {
        Condition condition = new Condition(MenuButtonEntity.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("parentId", menu.getMenuId());
        List<MenuButtonEntity> menuButtonEntities = menuButtonMapper.selectByCondition(condition);
        if (menuButtonEntities != null && menuButtonEntities.size() > 0) {
            ErrorMessageUtils.setErrorMessage("请先删除子菜单");
        }
        //菜单管理和角色管理为必有菜单
        if (menu.getMenuId().equals("1")) {
            ErrorMessageUtils.setErrorMessage("此项为系统固有菜单，不能删除");
        }
        int i = menuButtonMapper.deleteByPrimaryKey(menu.getMenuId());
        if (i > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "删除失败");
    }

    private List<MenuButtonEntity> getMenuNodes(MenuButtonEntity menu) {
        //组装条件查询
        Condition cond = new Condition(MenuButtonEntity.class);
        Criteria criteria = cond.or();
        criteria.andEqualTo(MenuButtonEntity.key.parentId.toString(), menu.getMenuId());
        //enable的值为-1时查询全部生效和不生效的
        Integer enable = menu.getAliveFlag();
        if (enable != null && enable != -1) {
            criteria.andEqualTo(MenuButtonEntity.key.aliveFlag.toString(), DataCenterCollections.YesOrNo.YES.value);
        }
        List<MenuButtonEntity> menuList = menuButtonMapper.selectByCondition(cond);
        return menuList;
    }

    //获取生效和不生效的（传了-1做处理）
    private void continueGetNodesZtree(List<MenuButtonEntity> menuList) {
        if (CollectionUtil.isNotEmpty(menuList)) {
            for (int i = 0; i < menuList.size(); i++) {
                MenuButtonEntity menuButtonEntity = menuList.get(i);
                //新建对像传-1为查询生效和不生效
                MenuButtonEntity entity = new MenuButtonEntity();
                entity.setMenuId(menuButtonEntity.getMenuId());
                entity.setAliveFlag(-1);
                List<MenuButtonEntity> list = getMenuNodes(entity);
                if (CollectionUtil.isNotEmpty(list)) {
                    menuList.get(i).setChildren(list);
                    continueGetNodesZtree(list);
                }
            }
        }
    }

    //只获取生效的
    private void continueGetNodesZtreeEnabl(List<MenuButtonEntity> menuList) {
        if (CollectionUtil.isNotEmpty(menuList)) {
            for (int i = 0; i < menuList.size(); i++) {
                List<MenuButtonEntity> list = getMenuNodes(menuList.get(i));
                if (CollectionUtil.isNotEmpty(list)) {
                    menuList.get(i).setChildren(list);
                    continueGetNodesZtreeEnabl(list);
                }
            }
        }
    }

    private void continueGetNodes(List<MenuButtonEntity> menuList) {
        if (CollectionUtil.isNotEmpty(menuList)) {
            for (int i = 0; i < menuList.size(); i++) {
                List<MenuButtonEntity> list = getMenuNodes(menuList.get(i));
                if (CollectionUtil.isNotEmpty(list)) {
                    menuList.addAll(list);
                    continueGetNodes(list);
                }
            }
        }
    }
}
