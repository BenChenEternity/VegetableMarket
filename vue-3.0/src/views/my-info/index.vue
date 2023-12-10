<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="头像URL">
        <el-input v-model="form.avatar" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.gender">
          <el-radio label="男" />
          <el-radio label="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="生日">
        <el-date-picker v-model="form.birthday" type="date" placeholder="Pick a date" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phoneNumber" />
      </el-form-item>
      <el-form-item>
        <el-button @click="dialogVisible = true">修改密码</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">修 改</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form ref="pwdForm" :model="user" label-width="80px">
        <el-form-item label="原密码" prop="originPassword">
          <el-input v-model="pwd.origin" type="password" placeholder="请输入原密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPssword">
          <el-input v-model="pwd.new" type="password" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="pwd.confirmNew" type="password" placeholder="请再次输入新密码"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handlePasswordChange">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import axios from 'axios';
export default {
  data()
  {
    return {
      form: {
        avatar: '',
        gender: '男',
        email: '',
        birthday: null,
        phoneNumber: ''
      },
      pwd: {
        origin: '',
        new: '',
        confirmNew: ''
      },
      dialogVisible: false,
    }
  },
  created()
  {
    this.fetchData()
  },
  methods: {
    fetchData()
    {
      const headers = {
        'user-id': Cookies.get('userId'),
        'session-token': Cookies.get('token'),
      };
      axios.post('https://ajie.cool/api/info', {}, { headers })
        .then(response =>
        {
          response.data.gender = (response.data.gender === false) ? "女" : "男";
          this.form = response.data;
        })
        .catch(error =>
        {
          console.error('Error fetching data:', error);
        })
    },

    handleSubmit()
    {
      const postData = {
        gender: this.form.gender === "女" ? false : true,
        email: this.form.email,
        avatar: this.form.avatar,
        phoneNumber: this.form.phoneNumber,
        birthday: this.form.birthday,
      };

      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };
      axios.post('https://ajie.cool/api/change-info', postData, { headers })
        .then(response =>
        {
          this.$message.success("修改成功");
          this.fetchData();
        })
        .catch(error =>
        {
          this.$message.error(error);
          console.error(error);
        });
    },

    handlePasswordChange()
    {
      if (this.pwd.origin === '')
      {
        this.$message.error("原密码不能为空");
        return;
      }
      if (this.pwd.origin.length < 6)
      {
        this.$message.error("原密码少于6位");
        return;
      }
      if (this.pwd.new === '')
      {
        this.$message.error("新密码不能为空");
        return;
      }
      if (this.pwd.new.length < 6)
      {
        this.$message.error("新密码少于6位");
        return;
      }
      if (this.pwd.new === this.pwd.confirmNew)
      {
        const postData = {
          'originPassword': this.pwd.origin,
          'newPassword': this.pwd.new
        };

        const userId = Cookies.get('userId');
        const token = Cookies.get('token');

        const headers = {
          'user-id': userId,
          'session-token': token,
        };
        axios.post('https://ajie.cool/api/change-password', postData, { headers })
          .then(response =>
          {
            console.log(response.data);
            if (response.data === 100)
            {
              this.$message.error("原密码错误");
            } else if (response.data === 200)
            {
              this.$message.success("修改成功");
              this.dialogVisible = false;
            } else
            {
              this.$message.error("修改失败");
            }
          })
          .catch(error =>
          {
            this.$message.error(error);
            console.error(error);
          });
      } else
      {
        this.$message.error("两次输入的密码不一致");
      }
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

