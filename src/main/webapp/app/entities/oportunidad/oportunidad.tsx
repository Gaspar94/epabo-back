import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './oportunidad.reducer';
import { IOportunidad } from 'app/shared/model/oportunidad.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOportunidadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Oportunidad extends React.Component<IOportunidadProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { oportunidadList, match } = this.props;
    return (
      <div>
        <h2 id="oportunidad-heading">
          <Translate contentKey="epaboApp.oportunidad.home.title">Oportunidads</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="epaboApp.oportunidad.home.createLabel">Create new Oportunidad</Translate>
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
                  <Translate contentKey="epaboApp.oportunidad.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.fecha">Fecha</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.ubicacion">Ubicacion</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.autor">Autor</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.tags">Tags</Translate>
                </th>
                <th>
                  <Translate contentKey="epaboApp.oportunidad.urlImagen">Url Imagen</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {oportunidadList.map((oportunidad, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${oportunidad.id}`} color="link" size="sm">
                      {oportunidad.id}
                    </Button>
                  </td>
                  <td>{oportunidad.descripcion}</td>
                  <td>
                    <TextFormat type="date" value={oportunidad.fecha} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{oportunidad.ubicacion}</td>
                  <td>{oportunidad.autor}</td>
                  <td>{oportunidad.email}</td>
                  <td>{oportunidad.tags}</td>
                  <td>{oportunidad.urlImagen}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${oportunidad.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${oportunidad.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${oportunidad.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ oportunidad }: IRootState) => ({
  oportunidadList: oportunidad.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Oportunidad);
