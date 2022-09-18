import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Tenant Dashboard',
    icon: 'settings-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'FEATURES',
    group: true,
  },
  {
    title: 'OnBoard Tenant',
    icon: 'plus-outline',
    link: '/pages/dashboard',
  },
  {
    title: 'Edit Tenant',
    icon: 'edit-2-outline',
    children: [
      {
        title: 'Update Tenant',
        link: '/pages/dashboard',
      },
      {
        title: 'Delete Tenant',
        link: '/pages/dashboard',
      }
    ],
  }
];
