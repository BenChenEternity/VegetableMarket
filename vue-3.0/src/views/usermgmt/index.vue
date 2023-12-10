<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryParamsForm" inline>
      <el-form-item label="昵称">
        <el-input v-model="queryParams.nickname" />
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="queryParams.gender" />
      </el-form-item>
      <el-form-item label="等级">
        <el-input v-model="queryParams.level" type="number" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">增加</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="增加用户" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form ref="userForm" :model="user" label-width="80px">
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="user.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="user.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="user.avatar" placeholder="请输入头像URL"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNumber">
          <el-input v-model="user.phoneNumber" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker v-model="user.birthday" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="等级" prop="level">
          <el-input v-model="user.level" type="number" placeholder="请输入等级"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" label="ID" width="120">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="昵称" width="200">
        <template slot-scope="scope">
          {{ scope.row.nickname }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="60">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" alt="Avatar" style="width: 40px; height: 40px; border-radius: 50%;" />
        </template>
      </el-table-column>
      <el-table-column label="性别" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.gender ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="220">
        <template slot-scope="scope">
          {{ scope.row.email }}
        </template>
      </el-table-column>
      <el-table-column label="电话号码" width="200">
        <template slot-scope="scope">
          {{ scope.row.phoneNumber }}
        </template>
      </el-table-column>
      <el-table-column label="生日" width="150">
        <template slot-scope="scope">
          {{ scope.row.birthday }}
        </template>
      </el-table-column>
      <el-table-column label="等级" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.level }}
        </template>
      </el-table-column>
      <el-table-column label="删除" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="danger" @click="deleteUser(scope.row.id)">
            <i class="el-icon-close"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage4"
        :page-sizes="[10, 20, 30, 40]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import axios from 'axios';
import _ from 'lodash';


export default {
  filters: {
    statusFilter(status)
    {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data()
  {
    return {
      list: null,
      listLoading: true,
      total: 0,
      currentPage: 1,
      pageSize: 10,
      queryParams: {
        nickname: null,
        gender: null,
        level: null,
        currentPage: null,
        pageSize: null,
      },
      dialogVisible: false,
      user: {
        nickname: '',
        password: '',
        gender: '',
        email: '',
        avatar: '',
        phoneNumber: '',
        birthday: '',
        level: null,
      },
    }
  },
  created()
  {
    this.fetchData()
  },
  methods: {
    fetchData()
    {
      this.listLoading = true;

      const apiUrl = 'https://ajie.cool/api/user';

      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };

      const params = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
      };

      const postData = _.cloneDeep(this.queryParams);
      if (this.queryParams.gender === '女')
      {
        postData.gender = false;
      } else if (this.queryParams.gender === '男')
      {
        postData.gender = true;
      } else
      {
        postData.gender = null;
      }

      axios.post(apiUrl, postData, { headers, params })
        .then(response =>
        {
          this.list = response.data.list;
          this.total = response.data.total;
        })
        .catch(error =>
        {
          console.error('Error fetching data:', error);
        })
        .finally(() =>
        {
          this.listLoading = false;
        });
    },

    search()
    {
      this.currentPage = 1;
      this.pageSize = 10;
      this.fetchData();
    },

    deleteUser(id)
    {
      console.log(id)
      this.listLoading = true;

      const apiUrl = 'https://ajie.cool/api/user/delete';

      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };

      const postData = { "id": id };

      axios.post(apiUrl, postData, { headers })
        .then(response =>
        {
          this.$message.success(response.data);
        })
        .catch(error =>
        {
          this.$message.error(error);
          console.log(error);
        })
        .finally(() =>
        {
          this.listLoading = false;
          this.fetchData();
        });
    },

    handleSizeChange(val)
    {
      console.log("size", val);
      this.pageSize = val;
      this.fetchData();
    },

    handleCurrentChange(val)
    {
      console.log("current", val);
      this.currentPage = val;
      this.fetchData();
    },

    handleClose(done)
    {
      done();
    },

    handleSubmit()
    {
      if (!this.user.nickname || !this.user.password || this.user.level === null)
      {
        this.$message.error('昵称、密码、等级不能为空');
        return;
      }
      const postData = {
        nickname: this.user.nickname,
        password: this.user.password,
        gender: this.user.gender === "女" ? false : true,
        email: this.user.email,
        avatar: this.user.avatar,
        phoneNumber: this.user.phoneNumber,
        birthday: this.user.birthday,
        level: this.user.level,
      };

      console.log(postData);
      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };
      axios.post('https://ajie.cool/api/user/insert', postData, { headers })
        .then(response =>
        {
          console.log(response);
          this.$message.success('用户添加成功');
          this.dialogVisible = false;
          this.fetchData();
        })
        .catch(error =>
        {
          this.$message.error('用户添加失败');
          console.error(error);
        });
    },
  },
}
</script>
