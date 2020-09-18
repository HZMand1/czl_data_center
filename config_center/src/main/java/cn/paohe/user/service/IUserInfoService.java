package cn.paohe.user.service;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.vo.framework.AjaxResult;

/**
 * TODO
 * 种子后台用户信息接口
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/22 9:36
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface IUserInfoService {

   /**TODO 获取全部用户信息
    * @param:  userEntity
    * @return: AjaxResult
    * @author:    黄芝民
    * @date:      2019/10/22 9:48
    * @throws
    */
    public AjaxResult queryUserAllList(UserEntity userEntity);

    /**TODO 分页获取用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 9:53 
     * @throws   
     */ 
    public  AjaxResult queryUserAllPage(UserEntity userEntity);

   /**TODO 根据ID获取用户信息
    * @param:  id
    * @return: AjaxResult
    * @author:    黄芝民
    * @date:      2019/10/22 9:59 
    * @throws
    */ 
    public AjaxResult queryUserById(UserEntity userEntity);

    /**TODO 根据账号获取后台用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 9:53
     * @throws
     */
    public  AjaxResult queryUserByAccount(UserEntity userEntity);

    /**TODO 新增后台用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 9:59 
     * @throws   
     */ 
    public AjaxResult insertUser(UserEntity userEntity);

   /**TODO 修改后台用户信息
    * @param:  userEntity
    * @return: AjaxResult
    * @author:    黄芝民
    * @date:      2019/10/22 9:58
    * @throws
    */
    public AjaxResult updateUserById(UserEntity userEntity);

    /**TODO 删除后台用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 9:58
     * @throws
     */
    public AjaxResult deleteUserById(UserEntity userEntity);

    /**TODO 启用、禁用后台用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 16:49 
     * @throws   
     */ 
    public AjaxResult enableUser(UserEntity userEntity);

    /**TODO 修改后台用户密码
     * @param: userEntity
     * @return:
     * @author:    黄芝民
     * @date:      2019/12/5 10:33
     * @throws
     */
 AjaxResult updateUserPassword(UserEntity userEntity);

 /**TODO 删除用户
  * @param ids
  * @return
  * @author 夏家鹏
  * @date 2019/12/5 13:37
  * @throws
  */
    AjaxResult delUser(String[] ids);
}
