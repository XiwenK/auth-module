import {ref} from 'vue';

export default function () {
  const loading = ref(false);

  //pagination
  const pagination = ref({
    sortBy: 'name',
    descending: false,
    page: 1,
    rowsPerPage: 5,
    rowsNumber: 10
  });

  //filter
  const filter = ref(null);

  const rows = ref([]);

  //model
  const model = ref({});

  //data loader
  const dataLoader = ref(null);
  const dataCountLoader = ref(null);
  const load = (props = {pagination: pagination.value, filter: filter.value}) => {
    const { page, rowsPerPage, sortBy, descending } = props.pagination;
    const filter = props.filter;
    loading.value = true;
    const string = filter ? filter : "";

    if (dataCountLoader.value){
      dataCountLoader.value(string).then(res => {
        pagination.value.rowsNumber = res;
        const fetchCount = rowsPerPage === 0 ? pagination.value.rowsNumber : rowsPerPage;
        dataLoader.value(string, page - 1, fetchCount, sortBy, descending).then(res => {
          rows.value.splice(0, rows.value.length, ...res);
          onLoaded(res);
        });
        pagination.value.page = page;
        pagination.value.rowsPerPage = rowsPerPage;
        pagination.value.sortBy = sortBy;
        pagination.value.descending = descending;
      });
    } else {
      dataLoader.value(string).then(res => {
        rows.value.splice(0, rows.value.length, ...res);
        onLoaded(res);
      });
    }
    loading.value = false;
  };

  const loadListeners = ref([]);
  const onLoaded = (data) => {
    loadListeners.value.forEach(listener => listener(data));
  }



  return {
    loading,
    pagination,
    filter,
    rows,
    model,
    load,
    dataLoader,
    dataCountLoader,
    loadListeners
  }
}
