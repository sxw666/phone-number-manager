package www.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import www.entity.UserRole;
import www.service.UserRoleService;

/**
 * 系统用户角色业务实现
 *
 * @author 廿二月的天
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {
    @Override
    public Map<String, Object> findRolesAndSystemUsersAll(Integer pageNum, Integer pageSize) throws Exception {
        setPageHelper(pageNum, pageSize);
        List<UserRole> data = userRolesDao.selectRolesAndSystemUsersAll();
        return findObjectsMethod(data);
    }

    @Override
    public Map<String, Object> findRolesAndPrivileges(Integer pageNum, Integer pageSize) throws Exception {
        setPageHelper(pageNum, pageSize);
        List<UserRole> data = userRolesDao.selectRolesAndPrivilegesAll();
        return findObjectsMethod(data);
    }

    @Override
    public UserRole findRolesAndPrivileges(Integer roleId) throws Exception {
        return userRolesDao.selectRolesAndPrivilegesById(roleId);
    }

    @Override
    public List<UserRole> findRolesByRoleName(String roleName) throws Exception {
        return userRolesDao.selectObjectsByName(roleName);
    }
}
