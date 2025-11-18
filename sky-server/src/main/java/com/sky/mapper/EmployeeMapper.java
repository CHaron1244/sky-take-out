package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {


    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee (username,password,name,phone,sex,id_number,status,create_time,update_time,create_user,update_user)" +
            " values(#{username},#{password},#{name},#{phone},#{sex},#{idNumber},#{status},#{createTime}," +
            "#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Update({
            "<script>",
            "update employee",
            "<set>",
            "  <if test='name != null'>name = #{name},</if>",
            "  <if test='username != null'>username = #{username},</if>",
            "  <if test='password != null'>password = #{password},</if>",
            "  <if test='phone != null'>phone = #{phone},</if>",
            "  <if test='sex != null'>sex = #{sex},</if>",
            "  <if test='idNumber != null'>id_number = #{idNumber},</if>",
            "  <if test='updateTime != null'>update_time = #{updateTime},</if>",
            "  <if test='updateUser != null'>update_user = #{updateUser},</if>",
            "  <if test='status != null'>status = #{status},</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
