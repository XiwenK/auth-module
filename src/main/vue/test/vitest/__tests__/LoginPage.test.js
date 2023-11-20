import { render, fireEvent, waitFor } from '@testing-library/vue'
import Login from 'pages/Login/LoginPage.vue';
import { createI18n } from 'vue-i18n';
import messages from "src/i18n";
import {Quasar} from 'quasar';
import { describe, it } from 'vitest';
import { createTestingPinia } from '@pinia/testing';

const i18n = createI18n({
  locale: 'en-US',
  legacy: false,
  globalInjection: true,
  messages
});
describe('Login', () => {
  it('should show login form', async () => {
    const { getByText } = render(Login, {
      global: {
        plugins: [Quasar, i18n, createTestingPinia()]
      }
    })

    getByText('Email');
    getByText('Password');
    getByText('Login');
    getByText('Login to Replace');
  })

  it('should show validation errors', async () => {
    const { getByText, getByLabelText } = render(Login, {
      global: {
        plugins: [Quasar, i18n]
      }
    })

    // Click on the login button to show the login form
    await fireEvent.click(getByText('Login'))
    await waitFor(() => getByText('Email must be set'));

    // Enter invalid email
    await fireEvent.update(getByLabelText('Email'), 'invalid');
    await fireEvent.click(getByText('Login'))
    await waitFor(() => getByText('Email must be valid'));

    // Enter valid email
    await fireEvent.update(getByLabelText('Email'), 'valid@mail.ru');
    await fireEvent.click(getByText('Login'))

    await waitFor(() => getByText('Password must be set'))


  })
})
