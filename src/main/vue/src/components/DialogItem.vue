<template>
  <div class="dialog-item">
    <q-item v-if="type === 'string'"
            dense>
      <q-item-section>
        <q-input densex
                 :label="label"
                 v-model="localVal"
                 lazy-rules="ondemand"
                 :prefix="prefix"
                 :rules="rules">
          <template v-slot:append>
            <slot/>
          </template>
        </q-input>
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'textarea'"
            dense>
      <q-item-section>
        <q-input dense
                 :label="label"
                 v-model="localVal"
                 lazy-rules="ondemand"
                 type="textarea"
                 rows="2"
                 :rules="rules"/>
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'email'">
      <q-item-section>
        <q-input
          dense
          :label="label"
          v-model="localVal"
          type="email"
          :suffix="suffix"
          lazy-rules="ondemand"
          :rules="rules"/>
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'password'">
      <q-item-section>
        <q-input
          dense
          :label="label"
          v-model="localVal"
          type="password"
          :rules="rules || [val => val && val.length >=8 || 'Min password length is 8']"
          lazy-rules="ondemand">
        </q-input>
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'multiselect'"
            dense>
      <q-item-section>
        <q-select
          dense
          :label="label"
          v-model="localVal"
          use-input
          use-chips
          multiple
          input-debounce="0"
          :options="filterOptions"
          @filter="filterOpt"
          lazy-rules="ondemand"
          :rules="rules"
        />
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'multiselectWithAdd'"
            dense>
      <q-item-section>
        <q-select
          dense
          :label="label"
          v-model="localVal"
          use-input
          use-chips
          multiple
          input-debounce="0"
          :options="filterOptions"
          @filter="filterOpt"
          @new-value="createValue"
          lazy-rules="ondemand"
          :rules="rules"
        />
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'select'"
            dense>
      <q-item-section>
        <q-select
          dense
          :label="label"
          v-model="localVal"
          :options="filterOptions"
          lazy-rules="ondemand"
          :rules="rules"
          :disable="disable"
        />
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'toggler'" class="row"
            dense>
      <q-item-section input-class="text-right">
        <q-toggle
          :label="label"
          left-label
          v-model="localVal"
        />
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'date'" class="row"
            dense>
      <q-item-section>
        <q-input dense
                 :label="label"
                 v-model="localVal"
                 lazy-rules="ondemand"
                 :rules="rules">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy" @before-show="updateProxyDate" cover transition-show="scale"
                             transition-hide="scale">
                <q-date v-model="proxyDate" :mask="dateFormat">
                  <div class="row items-center justify-end">
                    <q-btn :label="$t('buttons.cancel')" color="primary" flat v-close-popup/>
                    <q-btn :label="$t('buttons.ok')" color="primary" flat @click="saveDate" v-close-popup/>
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
      </q-item-section>
    </q-item>
    <q-item v-if="type === 'number'" class="row"
            dense>
      <q-item-section>
        <q-input dense
                 :label="label"
                 v-model.number="localVal"
                 lazy-rules="ondemand"
                 :rules="rules"/>
      </q-item-section>
    </q-item>
  </div>
</template>

<script setup>
    import {ref, defineProps, watch, defineEmits, computed} from 'vue';
    import moment from 'moment';
    import { useI18n } from "vue-i18n";

    const { t } = useI18n();

    const props = defineProps(['modelValue', 'type', 'label', 'suffix', 'rules', 'options', 'lazy-rules', 'dateFormat', 'showTime', 'prefix', 'disable']);
    const emit = defineEmits(['update:modelValue']);

    const localVal = ref(props.modelValue);
    watch(() => props.modelValue, (newVal) => {
        localVal.value = newVal;
    });

    const proxyDate = ref(props.modelValue);

    watch(localVal, (newVal) => {
        emit('update:modelValue', newVal);
    }, {deep: true});

    const filterOptions = ref(props.options);

    watch(() => props.options, (newVal) => {
      filterOptions.value = newVal;
    });

    const inputDate = ref('');
    const filterOpt = (val, update) =>  {
        update(() => {
            if (val === '') {
                filterOptions.value = props.options
            } else {
                const needle = val.toLowerCase();
                filterOptions.value = props.options.filter(
                    v => v.label.toLowerCase().indexOf(needle) > -1
                )
            }
        })
    }

    const createValue = (val, done) => {
      if (val.length > 2) {
        if (!props.options.includes(val)) {
          done(val, 'add')
        }
      }
    }

    const formattedDate = computed(() => {
        if (localVal.value) {
            return moment(localVal.value).format(props.dateFormat || 'YYYY-MM-DD');
        }
        return ''
    });

    const updateProxyDate = () => {
        proxyDate.value = localVal.value;
    };

    const saveDate = () => {
      localVal.value = proxyDate.value;
    };


</script>

<style scoped>

</style>
