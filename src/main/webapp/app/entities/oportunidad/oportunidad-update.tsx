import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './oportunidad.reducer';
import { IOportunidad } from 'app/shared/model/oportunidad.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOportunidadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOportunidadUpdateState {
  isNew: boolean;
}

export class OportunidadUpdate extends React.Component<IOportunidadUpdateProps, IOportunidadUpdateState> {
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
      const { oportunidadEntity } = this.props;
      const entity = {
        ...oportunidadEntity,
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
    this.props.history.push('/entity/oportunidad');
  };

  render() {
    const { oportunidadEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="epaboApp.oportunidad.home.createOrEditLabel">
              <Translate contentKey="epaboApp.oportunidad.home.createOrEditLabel">Create or edit a Oportunidad</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : oportunidadEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="oportunidad-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="descripcionLabel" for="descripcion">
                    <Translate contentKey="epaboApp.oportunidad.descripcion">Descripcion</Translate>
                  </Label>
                  <AvField
                    id="oportunidad-descripcion"
                    type="text"
                    name="descripcion"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaLabel" for="fecha">
                    <Translate contentKey="epaboApp.oportunidad.fecha">Fecha</Translate>
                  </Label>
                  <AvField id="oportunidad-fecha" type="date" className="form-control" name="fecha" />
                </AvGroup>
                <AvGroup>
                  <Label id="ubicacionLabel" for="ubicacion">
                    <Translate contentKey="epaboApp.oportunidad.ubicacion">Ubicacion</Translate>
                  </Label>
                  <AvField
                    id="oportunidad-ubicacion"
                    type="text"
                    name="ubicacion"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="autorLabel" for="autor">
                    <Translate contentKey="epaboApp.oportunidad.autor">Autor</Translate>
                  </Label>
                  <AvField
                    id="oportunidad-autor"
                    type="string"
                    className="form-control"
                    name="autor"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    <Translate contentKey="epaboApp.oportunidad.email">Email</Translate>
                  </Label>
                  <AvField
                    id="oportunidad-email"
                    type="text"
                    name="email"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tagsLabel" for="tags">
                    <Translate contentKey="epaboApp.oportunidad.tags">Tags</Translate>
                  </Label>
                  <AvField
                    id="oportunidad-tags"
                    type="text"
                    name="tags"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="urlImagenLabel" for="urlImagen">
                    <Translate contentKey="epaboApp.oportunidad.urlImagen">Url Imagen</Translate>
                  </Label>
                  <AvField id="oportunidad-urlImagen" type="text" name="urlImagen" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/oportunidad" replace color="info">
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
  oportunidadEntity: storeState.oportunidad.entity,
  loading: storeState.oportunidad.loading,
  updating: storeState.oportunidad.updating,
  updateSuccess: storeState.oportunidad.updateSuccess
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
)(OportunidadUpdate);
