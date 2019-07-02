<template>
  <div>
    <!-- 卡片 -->
    <v-card>
      <!-- 卡片的头部 -->
      <v-card-title>
        <v-btn color="primary" @click="addBrand()">新增</v-btn>
        <!-- 空间隔离组件 -->
        <v-spacer />
        <v-text-field label="输入关键字搜索" v-model="search" append-icon="search" hide-details />
      </v-card-title>
      <!-- 分割线 -->
      <v-divider />
      <!-- 卡片的内容 -->
      <v-data-table :headers="headers" :items="brands" :pagination.sync="pagination" :total-items="totalBrands"
        :loading="loading" class="elevation-1">
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td class="text-xs-center">{{ props.item.name }}</td>
          <td class="text-xs-center">
            <img v-if="props.item.image" :src="props.item.image" />
            <span v-else><i>NAN</i></span>
          </td>
          <td class="text-xs-center">{{ props.item.letter }}</td>
          <td class="text-xs-center">
            <v-btn color="info">编辑</v-btn>
            <v-btn color="warning">删除</v-btn>
          </td>
        </template>
      </v-data-table>
    </v-card>

    <!-- 品牌编辑弹窗 -->
    <v-dialog v-model="dialog" width="500" persistent>
      <brand-form @close="closeWindow"></brand-form>
    </v-dialog>
  </div>
</template>

<script>
  import BrandForm from "./BrandForm"
  export default {
    name: 'my-brand',
    components: {
      BrandForm
    },
    data() {
      return {
        // 总条数
        totalBrands: 0,
        // 当前页品牌数据
        brands: [],
        // 是否正在加载
        loading: true,
        // 分页信息
        pagination: {},
        // 搜索框内容
        search: '',
        // 控制品牌编辑弹窗的显示
        dialog: false,
        //  表头
        headers: [{
            text: 'id',
            align: 'center',
            value: 'id'
          },
          {
            text: '名称',
            align: 'center',
            sortable: false,
            value: 'name'
          },
          {
            text: 'LOGO',
            align: 'center',
            sortable: false,
            value: 'image'
          },
          {
            text: '首字母',
            align: 'center',
            value: 'letter',
            sortable: true,
          },
          {
            text: '操作',
            align: 'center',
            value: 'id',
            sortable: false
          }
        ]
      }
    },
    mounted() {
      // 页面渲染完成后加载数据
      this.getDataFromServer()
    },
    methods: {
      // 从服务器获取数据
      getDataFromServer() {
        // 发起请求
        this.$http.get("/item/brand/page", {
          params: {
            key: this.search,
            page: this.pagination.page,
            rows: this.pagination.rows,
            sortBy: this.pagination.sortBy,
            desc: this.pagination.descending
          }
        }).then(resp => {
          // 进度条开始加载
          this.loading = true
          // 赋值给对应的属性
          this.brands = resp.data.items
          this.totalBrands = resp.data.total
          // 进度条停止加载
          this.loading = false
        })
      },
      closeWindow() {
        this.dialog = false
      },
      addBrand() {
        this.dialog = true
      },
    },
    watch: {
      pagination: {
        deep: true,
        handler() {
          this.getDataFromServer()
        }
      },
      search: {
        handler() {
          this.getDataFromServer()
        }
      }
    }
  }
</script>