import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gasto from './gasto';
import GastoDetail from './gasto-detail';
import GastoUpdate from './gasto-update';
import GastoDeleteDialog from './gasto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GastoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GastoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GastoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Gasto} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={GastoDeleteDialog} />
  </>
);

export default Routes;
