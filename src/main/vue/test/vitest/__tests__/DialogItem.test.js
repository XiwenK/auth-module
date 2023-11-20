import {render, fireEvent, getByLabelText, screen} from '@testing-library/vue';
import FormInput from '../../../src/components/DialogItem.vue';
import { describe, expect, it } from 'vitest';
import {Quasar} from 'quasar';
import messages from 'src/i18n';
import { createI18n } from 'vue-i18n';
import moment from 'moment';

const i18n = createI18n({
  locale: 'en-US',
  legacy: false,
  globalInjection: true,
  messages
});

describe('FormInput', () => {
  it('renders the component for string type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'string',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for email type', () => {
    const {getByText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'email',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByText('Test Label')).toBeDefined();
  });

  it('renders the component for password type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'password',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for multiselect type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: [],
        type: 'multiselect',
        label: 'Test Label',
        options: [
          {label: 'Option 1', value: 'option1'},
          {label: 'Option 2', value: 'option2'},
          {label: 'Option 3', value: 'option3'},
        ],
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for select type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'select',
        label: 'Test Label',
        options: [
          {label: 'Option 1', value: 'option1'},
          {label: 'Option 2', value: 'option2'},
          {label: 'Option 3', value: 'option3'},
        ],
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for toggler type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: false,
        type: 'toggler',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for date type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'date',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('renders the component for number type', () => {
    const {getByLabelText} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'number',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    expect(getByLabelText('Test Label')).toBeDefined();
  });

  it('emits an update event on input for string type', async () => {
    const {getByLabelText, emitted} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'string',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    const input = getByLabelText('Test Label');
    await fireEvent.update(input, 'Test Value');
    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual(['Test Value']);
  });

  it('emits an update event on input for email type', async () => {
    const {getByLabelText, emitted} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'email',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    const input = getByLabelText('Test Label');
    await fireEvent.update(input, 'test@example.com');

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual(['test@example.com']);
  });

  it('emits an update event on input for password type', async () => {
    const {getByLabelText, emitted} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'password',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    const input = getByLabelText('Test Label');
    await fireEvent.update(input, 'password123');

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual(['password123']);
  });

  it('emits an update event on input for multiselect type', async () => {
    const {emitted, container} = render(FormInput, {
      props: {
        modelValue: [],
        type: 'multiselect',
        label: 'Test Label',
        options: [
          {label: 'Option 1', value: 'option1'},
          {label: 'Option 2', value: 'option2'},
          {label: 'Option 3', value: 'option3'},
        ],
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });


    const select = container.querySelector('[aria-label="Test Label"]');
    await fireEvent.click(select);

    const option1 = await screen.findByRole('option', { name: 'Option 1' });
    const option2 = await screen.findByRole('option', { name: 'Option 2' });

    await fireEvent.click(option1);
    await fireEvent.click(option2);

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][1]).toEqual([[{label: 'Option 1', value: 'option1'}, {label: 'Option 2', value: 'option2'}]]);
  });

  it('emits an update event on input for select type', async () => {
    const {emitted, container} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'select',
        label: 'Test Label',
        options: [
          {label: 'Option 1', value: 'option1'},
          {label: 'Option 2', value: 'option2'},
          {label: 'Option 3', value: 'option3'},
        ],
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });
    const select = container.querySelector('[aria-label="Test Label"]');
    await fireEvent.click(select);

    const option1 = await screen.findByRole('option', { name: 'Option 1' });
    await fireEvent.click(option1);

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual([{label: 'Option 1', value: 'option1'}]);
  });

  it('emits an update event on input for toggler type', async () => {
    const {getByLabelText, emitted} = render(FormInput, {
      props: {
        modelValue: false,
        type: 'toggler',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });
    const toggle = getByLabelText('Test Label');
    await fireEvent.click(toggle);

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual([true]);
  });

  it('emits an update event on input for date type', async () => {
    const format = 'YYYY/MM/DD';
    const { emitted, getByText } = render(FormInput, {
      props: {
        modelValue: '',
        type: 'date',
        label: 'Test Label',
        format: format,
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    const icon = getByText('event');
    await fireEvent.click(icon);

    const currentDate = moment().add(1, 'day');
    const dayOfMonth = currentDate.date();

    const formattedDate = currentDate.format(format);

    const dayButton = getByText(dayOfMonth);
    const okButton = getByText('Ok');

    await fireEvent.click(dayButton);
    await fireEvent.click(okButton);

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual([formattedDate]);
  });


  it('emits an update event on input for number type', async () => {
    const {getByLabelText, emitted} = render(FormInput, {
      props: {
        modelValue: '',
        type: 'number',
        label: 'Test Label',
      },
      global: {
        plugins: [Quasar, i18n]
      }
    });

    const input = getByLabelText('Test Label');
    await fireEvent.update(input, '42');

    expect(emitted()).toHaveProperty('update:modelValue');
    expect(emitted()['update:modelValue'][0]).toEqual([42]);
  });
});
