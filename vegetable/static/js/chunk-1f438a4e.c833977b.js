(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1f438a4e"],{"34d9":function(e,r,t){},"6d7f":function(e,r,t){},d5c2:function(e,r,t){"use strict";t.r(r);var s=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"login-container"},[t("el-form",{ref:"userForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"auto-complete":"on","label-position":"left"}},[t("div",{staticClass:"title-container"},[t("h3",{staticClass:"title"},[e._v("买菜系统注册")])]),t("el-form-item",{attrs:{label:"昵称",prop:"nickname",required:""}},[t("el-input",{attrs:{placeholder:"请输入昵称"},model:{value:e.user.nickname,callback:function(r){e.$set(e.user,"nickname",r)},expression:"user.nickname"}})],1),t("el-form-item",{attrs:{label:"密码",prop:"password",required:""}},[t("el-input",{attrs:{type:"password",placeholder:"请输入密码"},model:{value:e.user.password,callback:function(r){e.$set(e.user,"password",r)},expression:"user.password"}})],1),t("el-form-item",{attrs:{label:"性别",prop:"gender"}},[t("el-radio-group",{model:{value:e.user.gender,callback:function(r){e.$set(e.user,"gender",r)},expression:"user.gender"}},[t("el-radio",{attrs:{label:"男"}},[e._v("男")]),t("el-radio",{attrs:{label:"女"}},[e._v("女")])],1)],1),t("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[t("el-input",{attrs:{placeholder:"请输入邮箱"},model:{value:e.user.email,callback:function(r){e.$set(e.user,"email",r)},expression:"user.email"}})],1),t("el-form-item",{attrs:{label:"头像",prop:"avatar"}},[t("el-input",{attrs:{placeholder:"请输入头像URL"},model:{value:e.user.avatar,callback:function(r){e.$set(e.user,"avatar",r)},expression:"user.avatar"}})],1),t("el-form-item",{attrs:{label:"电话",prop:"phoneNumber"}},[t("el-input",{attrs:{placeholder:"请输入电话号码"},model:{value:e.user.phoneNumber,callback:function(r){e.$set(e.user,"phoneNumber",r)},expression:"user.phoneNumber"}})],1),t("el-form-item",{attrs:{label:"生日",prop:"birthday"}},[t("el-date-picker",{attrs:{type:"date",placeholder:"选择日期"},model:{value:e.user.birthday,callback:function(r){e.$set(e.user,"birthday",r)},expression:"user.birthday"}})],1),t("div",{staticStyle:{display:"flex","justify-content":"space-between"}},[t("el-button",{staticStyle:{width:"40%","margin-bottom":"30px"},attrs:{loading:e.loading},on:{click:e.returnLogin}},[e._v("返回")]),t("el-button",{staticStyle:{width:"40%","margin-bottom":"30px"},attrs:{loading:e.loading,type:"primary"},on:{click:e.handleRegister}},[e._v("提交注册")])],1)],1)],1)},a=[],l=t("bc3a").default,o={name:"Login",data:function(){return{user:{nickname:"",password:"",gender:"",email:"",avatar:"",phoneNumber:"",birthday:""}}},methods:{returnLogin:function(){this.$router.push("/login")},handleRegister:function(){var e=this;if(this.user.nickname)if(this.user.password)if(this.user.password.length<6)this.$message.error("密码不能少于6位");else{var r={nickname:this.user.nickname,password:this.user.password,gender:"女"!==this.user.gender,email:this.user.email,avatar:this.user.avatar,phoneNumber:this.user.phoneNumber,birthday:this.user.birthday};console.log(r),l.post("https://ajie.cool/api/auth/register",r).then((function(r){console.log(r);var t=r.data.code;"101"===t?e.$message.error("用户名已被注册"):"100"===t?(e.$message.success("注册成功"),e.$router.push("/login")):e.$message.error("注册出现异常")})).catch((function(r){e.$message.error("注册失败"),console.error(r)}))}else this.$message.error("密码不能为空");else this.$message.error("用户名不能为空")}}},i=o,n=(t("d9b6"),t("d817"),t("2877")),u=Object(n["a"])(i,s,a,!1,null,"3cb4c274",null);r["default"]=u.exports},d817:function(e,r,t){"use strict";t("6d7f")},d9b6:function(e,r,t){"use strict";t("34d9")}}]);