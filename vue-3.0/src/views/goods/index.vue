<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryParamsForm" inline>
      <el-form-item label="名称">
        <el-input v-model="queryParams.name" />
      </el-form-item>
      <el-form-item label="生产日期">
        <el-date-picker v-model="queryParams.productionDate" type="date" placeholder="选择日期" />
      </el-form-item>
      <el-form-item label="保质期">
        <el-input v-model="queryParams.shelfLife" type="number" />
      </el-form-item>
      <el-form-item label="上架日期">
        <el-date-picker v-model="queryParams.shelfDate" type="date" placeholder="选择日期" />
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="queryParams.price" type="number" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input v-model="queryParams.stock" type="number" />
      </el-form-item>
      <el-form-item label="供应商">
        <el-input v-model="queryParams.supplier" />
      </el-form-item>
      <el-form-item label="打折">
        <el-input v-model="queryParams.sale" type="number" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="isNotNormalUser" type="primary" @click="dialogVisible = true">增加</el-button>
      </el-form-item>
    </el-form>
    <el-dialog v-if="isNotNormalUser" title="增加蔬菜" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form ref="vegetableForm" :model="vegetable" label-width="80px">
        <el-form-item label="蔬菜名称" prop="name">
          <el-input v-model="vegetable.name" placeholder="请输入蔬菜名称"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="vegetable.description" placeholder="请输入蔬菜描述"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="vegetable.unit" placeholder="请输入单位"></el-input>
        </el-form-item>
        <el-form-item label="生产日期" prop="productionDate">
          <el-date-picker v-model="vegetable.productionDate" type="date" placeholder="选择生产日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="保质期" prop="shelfLife">
          <el-input v-model="vegetable.shelfLife" placeholder="请输入保质期"></el-input>
        </el-form-item>
        <el-form-item label="上架日期" prop="shelfDate">
          <el-date-picker v-model="vegetable.shelfDate" type="date" placeholder="选择上架日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="vegetable.price" placeholder="请输入价格"></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="vegetable.stock" placeholder="请输入库存"></el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-input v-model="vegetable.supplier" placeholder="请输入供应商"></el-input>
        </el-form-item>
        <el-form-item label="折扣" prop="sale">
          <el-input v-model="vegetable.sale" placeholder="请输入折扣（0~10折）"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" label="ID" width="80">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="名称" width="140">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="描述" width="220">
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column label="单位" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.unit }}
        </template>
      </el-table-column>
      <el-table-column label="生产日期" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.productionDate }}
        </template>
      </el-table-column>
      <el-table-column label="保质期" width="80" align="center">
        <template slot-scope="scope">
          {{ scope.row.shelfLife }}
        </template>
      </el-table-column>
      <el-table-column label="上架日期" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.shelfDate }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="80" align="center">
        <template slot-scope="scope">
          <span v-if="isVIP">
            <span style="text-decoration: line-through; color: grey;">
              {{ scope.row.price }}<br>
            </span>
            <span>{{ (scope.row.price * (scope.row.sale / 10)).toFixed(2) }}</span>
          </span>
          <span v-else>
            {{ scope.row.price }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="库存" width="80" align="center">
        <template slot-scope="scope">
          {{ scope.row.stock }}
        </template>
      </el-table-column>
      <el-table-column label="供应商" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.supplier }}
        </template>
      </el-table-column>
      <el-table-column label="折扣" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.sale }}
        </template>
      </el-table-column>
      <el-table-column v-if="isNotNormalUser" label="删除" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="danger" @click="deleteVegetable(scope.row.id)">
            <i class="el-icon-close"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage4"
        :page-sizes="[10, 20, 30, 40]" :page-size="100" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

  
<script>
import Cookies from "js-cookie";
const axios = require("axios").default;

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
  computed: {
    isNotNormalUser()
    {
      const level = Cookies.get('level');
      console.log(level)
      return parseInt(level) >= 3;
    },
    isVIP()
    {
      const level = Cookies.get('level');
      console.log(level)
      return parseInt(level) >= 2;
    },
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
        name: null,
        productionDate: null,
        shelfLife: null,
        shelfDate: null,
        price: null,
        stock: null,
        supplier: null,
        sale: null,
        currentPage: null,
        pageSize: null,
      },
      dialogVisible: false,
      vegetable: {
        name: null,
        description: null,
        unit: null,
        productionDate: null,
        shelfLife: null,
        shelfDate: null,
        price: null,
        stock: null,
        supplier: null,
        sale: null
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

      const apiUrl = 'https://ajie.cool/api/vegetable';

      const params = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
      };

      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };

      const postData = { ...this.queryParams };
      console.log(postData);

      console.log(axios);
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
      this.fetchData();
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

    deleteVegetable(id)
    {
      console.log(id)
      this.listLoading = true;

      const apiUrl = 'https://ajie.cool/api/vegetable/delete';

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

    handleSubmit()
    {
      if (!this.vegetable.name)
      {
        this.$message.error('蔬菜名称不能为空');
        return;
      }

      if (!this.vegetable.description)
      {
        this.$message.error('蔬菜描述不能为空');
        return;
      }

      if (this.vegetable.unit === null)
      {
        this.$message.error('蔬菜单位不能为空');
        return;
      }

      if (!this.vegetable.productionDate)
      {
        this.$message.error('蔬菜生产日期不能为空');
        return;
      }

      if (this.vegetable.shelfLife === null)
      {
        this.$message.error('蔬菜保质期不能为空');
        return;
      }

      if (!this.vegetable.shelfDate)
      {
        this.$message.error('蔬菜上架日期不能为空');
        return;
      }

      if (this.vegetable.price === null)
      {
        this.$message.error('蔬菜价格不能为空');
        return;
      }

      if (this.vegetable.stock === null)
      {
        this.$message.error('蔬菜库存不能为空');
        return;
      }

      if (!this.vegetable.supplier)
      {
        this.$message.error('蔬菜供应商不能为空');
        return;
      }

      if (this.vegetable.sale === null)
      {
        this.$message.error('蔬菜折扣不能为空');
        return;
      }

      const postData = {
        name: this.vegetable.name,
        description: this.vegetable.description,
        unit: this.vegetable.unit,
        productionDate: this.vegetable.productionDate,
        shelfLife: this.vegetable.shelfLife,
        shelfDate: this.vegetable.shelfDate,
        price: this.vegetable.price,
        stock: this.vegetable.stock,
        supplier: this.vegetable.supplier,
        sale: this.vegetable.sale,
      };

      console.log(postData);

      const userId = Cookies.get('userId');
      const token = Cookies.get('token');

      const headers = {
        'user-id': userId,
        'session-token': token,
      };

      axios.post('https://ajie.cool/api/vegetable/insert', postData, { headers })
        .then(response =>
        {
          console.log(response);
          this.$message.success('蔬菜添加成功');
          this.dialogVisible = false;
          this.fetchData();
        })
        .catch(error =>
        {
          this.$message.error('蔬菜添加失败');
          console.error(error);
        });
    },
  },
}
</script>
  