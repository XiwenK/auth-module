import { render} from '@testing-library/vue'
import GroupsList from '../../../src/pages/Groups/GroupsList.vue';
import { describe, expect, it } from 'vitest';
import {Quasar} from 'quasar';
import {createI18n} from "vue-i18n";
import messages from "src/i18n";
import { createTestingPinia} from "@pinia/testing";

const i18n = createI18n({
  locale: 'en-US',
  legacy: false,
  globalInjection: true,
  messages
});

describe('GroupsList', () => {
  it('renders table with expected values', async () => {
    const { getByText} = render(GroupsList, {
      global: {
        plugins: [Quasar, i18n, createTestingPinia({
          initialState: {
            user: {
              loggedIn: true,
              userDetail: {email: 'Test User'}
            }
          }
        })]
      }
    })

    // check table header labels
    expect(getByText('Id')).toBeDefined();
    expect(getByText('Group Name')).toBeDefined();
    expect(getByText('Date')).toBeDefined();
    expect(getByText('Places Count')).toBeDefined();
    expect(getByText('Booked')).toBeDefined();
    expect(getByText('Schedule')).toBeDefined();

    //TODO: check table data

    // check number of rows
    // const rows = await screen.findAllByRole('row');
    // expect(rows.length).toEqual(6) // 5 data rows + 1 header row
    //
    // // check cell values for each row
    // expect(getAllByText(/Group \d{1,2}/)[0]).toMatch(/Group \d{1,2}/);
    // expect(getAllByText(/\d{2}-\d{2}-\d{4}/)[0]).toMatch(/\d{2}-\d{2}-\d{4}/);
    // expect(getAllByText(/\d{1,2}/)[0]).toMatch(/\d{1,2}/);
    // expect(getAllByText(/\d{1,2}/)[0]).toMatch(/\d{1,2}/);

  })
})
