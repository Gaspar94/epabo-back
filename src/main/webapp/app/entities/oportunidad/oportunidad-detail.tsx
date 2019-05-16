import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './oportunidad.reducer';
import { IOportunidad } from 'app/shared/model/oportunidad.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOportunidadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OportunidadDetail extends React.Component<IOportunidadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { oportunidadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="epaboApp.oportunidad.detail.title">Oportunidad</Translate> [<b>{oportunidadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="descripcion">
                <Translate contentKey="epaboApp.oportunidad.descripcion">Descripcion</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.descripcion}</dd>
            <dt>
              <span id="fecha">
                <Translate contentKey="epaboApp.oportunidad.fecha">Fecha</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={oportunidadEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="ubicacion">
                <Translate contentKey="epaboApp.oportunidad.ubicacion">Ubicacion</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.ubicacion}</dd>
            <dt>
              <span id="autor">
                <Translate contentKey="epaboApp.oportunidad.autor">Autor</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.autor}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="epaboApp.oportunidad.email">Email</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.email}</dd>
            <dt>
              <span id="tags">
                <Translate contentKey="epaboApp.oportunidad.tags">Tags</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.tags}</dd>
            <dt>
              <span id="urlImagen">
                <Translate contentKey="epaboApp.oportunidad.urlImagen">Url Imagen</Translate>
              </span>
            </dt>
            <dd>{oportunidadEntity.urlImagen}</dd>
          </dl>
          <Button tag={Link} to="/entity/oportunidad" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/oportunidad/${oportunidadEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ oportunidad }: IRootState) => ({
  oportunidadEntity: oportunidad.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OportunidadDetail);
