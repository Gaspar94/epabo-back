import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './gasto.reducer';
import { IGasto } from 'app/shared/model/gasto.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGastoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IGastoUpdateState {
  isNew: boolean;
}

export class GastoUpdate extends React.Component<IGastoUpdateProps, IGastoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { gastoEntity } = this.props;
      const entity = {
        ...gastoEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/gasto');
  };

  render() {
    const { gastoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="epaboApp.gasto.home.createOrEditLabel">
              <Translate contentKey="epaboApp.gasto.home.createOrEditLabel">Create or edit a Gasto</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : gastoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="gasto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="descripcionLabel" for="descripcion">
                    <Translate contentKey="epaboApp.gasto.descripcion">Descripcion</Translate>
                  </Label>
                  <AvField
                    id="gasto-descripcion"
                    type="text"
                    name="descripcion"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaLabel" for="fecha">
                    <Translate contentKey="epaboApp.gasto.fecha">Fecha</Translate>
                  </Label>
                  <AvField id="gasto-fecha" type="date" className="form-control" name="fecha" />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaModificacionLabel" for="fechaModificacion">
                    <Translate contentKey="epaboApp.gasto.fechaModificacion">Fecha Modificacion</Translate>
                  </Label>
                  <AvField id="gasto-fechaModificacion" type="date" className="form-control" name="fechaModificacion" />
                </AvGroup>
                <AvGroup>
                  <Label id="montoGastoLabel" for="montoGasto">
                    <Translate contentKey="epaboApp.gasto.montoGasto">Monto Gasto</Translate>
                  </Label>
                  <AvField
                    id="gasto-montoGasto"
                    type="string"
                    className="form-control"
                    name="montoGasto"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="userIdLabel" for="userId">
                    <Translate contentKey="epaboApp.gasto.userId">User Id</Translate>
                  </Label>
                  <AvField
                    id="gasto-userId"
                    type="string"
                    className="form-control"
                    name="userId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/gasto" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  gastoEntity: storeState.gasto.entity,
  loading: storeState.gasto.loading,
  updating: storeState.gasto.updating,
  updateSuccess: storeState.gasto.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GastoUpdate);
