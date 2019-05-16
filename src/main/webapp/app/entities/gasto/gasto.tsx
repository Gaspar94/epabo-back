import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './gasto.reducer';
import { IGasto } from 'app/shared/model/gasto.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGastoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Gasto extends React.Component<IGastoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { gastoList, match } = this.props;
    return (
      <div>
        <h2 id="gasto-heading">
          <Translate contentKey="epaboApp.gasto.home.title">Gastos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="epaboApp.gasto.home.createLabel">Create new Gasto</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.gasto.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.gasto.fecha">Fecha</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.gasto.fechaModificacion">Fecha Modificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.gasto.montoGasto">Monto Gasto</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.gasto.userId">User Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gastoList.map((gasto, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${gasto.id}`} color="link" size="sm">
                      {gasto.id}
                    </Button>
                  </td>
                  <td>{gasto.descripcion}</td>
                  <td>
                    <TextFormat type="date" value={gasto.fecha} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={gasto.fechaModificacion} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{gasto.montoGasto}</td>
                  <td>{gasto.userId}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${gasto.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gasto.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gasto.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ gasto }: IRootState) => ({
  gastoList: gasto.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Gasto);
