import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OnboardEmployeeComponent} from './onboard-employee/onboard-employee.component';
import {EditEmployeeComponent} from './edit-employee/edit-employee.component';
import {ListEmployeeComponent} from './list-employee/list-employee.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Employee',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'view',
      },
      {
        path: 'onboard',
        component: OnboardEmployeeComponent,
        data: {
          title: 'Onboard',
        },
      },
      {
        path: 'edit',
        component: EditEmployeeComponent,
        data: {
          title: 'Edit',
        },
      },
      {
        path: 'view',
        component: ListEmployeeComponent,
        data: {
          title: 'View',
        },
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EmployeeRoutingModule {}

