import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gasto.reducer';
import { IGasto } from 'app/shared/model/gasto.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGastoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GastoDetail extends React.Component<IGastoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { gastoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="epaboApp.gasto.detail.title">Gasto</Translate> [<b>{gastoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="descripcion">
                <Translate contentKey="epaboApp.gasto.descripcion">Descripcion</Translate>
              </span>
            </dt>
            <dd>{gastoEntity.descripcion}</dd>
            <dt>
              <span id="fecha">
                <Translate contentKey="epaboApp.gasto.fecha">Fecha</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={gastoEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="fechaModificacion">
                <Translate contentKey="epaboApp.gasto.fechaModificacion">Fecha Modificacion</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={gastoEntity.fechaModificacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="montoGasto">
                <Translate contentKey="epaboApp.gasto.montoGasto">Monto Gasto</Translate>
              </span>
            </dt>
            <dd>{gastoEntity.montoGasto}</dd>
            <dt>
              <span id="userId">
                <Translate contentKey="epaboApp.gasto.userId">User Id</Translate>
              </span>
            </dt>
            <dd>{gastoEntity.userId}</dd>
          </dl>
          <Button tag={Link} to="/entity/gasto" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/gasto/${gastoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ gasto }: IRootState) => ({
  gastoEntity: gasto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GastoDetail);
