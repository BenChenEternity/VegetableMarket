<template>
  <div class="login-container">
    <el-form ref="userForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
      label-position="left">
      <div class="title-container">
        <h3 class="title">蔬菜商品销售系统注册</h3>
      </div>
      <el-form-item label="昵称" prop="nickname" required>
        <el-input v-model="user.nickname" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" required>
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
      <el-form-item label="电话" prop="phoneNumber">
        <el-input v-model="user.phoneNumber" placeholder="请输入电话号码"></el-input>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="user.birthday" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <div style="display: flex; justify-content: space-between;">
        <el-button :loading="loading" style="width: 40%; margin-bottom: 30px" @click="returnLogin">返回</el-button>
        <el-button :loading="loading" type="primary" style="width: 40%; margin-bottom: 30px;"
          @click="handleRegister">提交注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
const axios = require("axios").default;

export default {
  name: "Login",
  data()
  {
    return {
      user: {
        nickname: '',
        password: '',
        gender: '',
        email: '',
        avatar: '',
        phoneNumber: '',
        birthday: '',
      },
    }
  },
  methods: {
    returnLogin()
    {
      this.$router.push('/login');
    },
    handleRegister()
    {
      if (!this.user.nickname)
      {
        this.$message.error('用户名不能为空');
        return;
      }
      if (!this.user.password)
      {
        this.$message.error('密码不能为空');
        return;
      }
      if (this.user.password.length < 6)
      {
        this.$message.error('密码不能少于6位');
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
      };

      console.log(postData);
      axios.post('https://ajie.cool/api/auth/register', postData)
        .then(response =>
        {
          console.log(response);
          const status = response.data.code
          if (status === "101")
          {
            this.$message.error('用户名已被注册');
          } else if (status === "100")
          {
            this.$message.success('注册成功');
            this.$router.push('/login');
          } else
          {
            this.$message.error('注册出现异常');
          }

        })
        .catch(error =>
        {
          this.$message.error('注册失败');
          console.error(error);
        });
    },
  }
};
</script>

<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 500px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
