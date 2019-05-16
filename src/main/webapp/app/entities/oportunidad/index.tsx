import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Oportunidad from './oportunidad';
import OportunidadDetail from './oportunidad-detail';
import OportunidadUpdate from './oportunidad-update';
import OportunidadDeleteDialog from './oportunidad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OportunidadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OportunidadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OportunidadDetail} />
      <ErrorBoundaryRoute path={match.url} component={Oportunidad} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OportunidadDeleteDialog} />
  </>
);

export default Routes;
