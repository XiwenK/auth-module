import { boot } from 'quasar/wrappers';
import PageLayout from 'components/page/PageLayout.vue';
import PageHeader from "components/page/PageHeader.vue";
import PageBody from "components/page/PageBody.vue";
import PageFooter from "components/page/PageFooter.vue";
import PageHeaderBtnBack from "components/page/PageHeaderBtnBack.vue";
import ButtonCrud from "components/buttons/ButtonCrud.vue"
import FormPage from "components/page/FormPage.vue";

// "async" is optional;
// more info on params: https://v2.quasar.dev/quasar-cli/boot-files
export default boot(async ({app}) => {
  app.component('page-layout', PageLayout);
  app.component('page-header', PageHeader);
  app.component('page-body', PageBody);
  app.component('page-footer', PageFooter);
  app.component('page-header-btn-back', PageHeaderBtnBack);
  app.component('button-crud', ButtonCrud);
  app.component('form-page', FormPage);
})
