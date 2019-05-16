import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOportunidad, defaultValue } from 'app/shared/model/oportunidad.model';

export const ACTION_TYPES = {
  FETCH_OPORTUNIDAD_LIST: 'oportunidad/FETCH_OPORTUNIDAD_LIST',
  FETCH_OPORTUNIDAD: 'oportunidad/FETCH_OPORTUNIDAD',
  CREATE_OPORTUNIDAD: 'oportunidad/CREATE_OPORTUNIDAD',
  UPDATE_OPORTUNIDAD: 'oportunidad/UPDATE_OPORTUNIDAD',
  DELETE_OPORTUNIDAD: 'oportunidad/DELETE_OPORTUNIDAD',
  RESET: 'oportunidad/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOportunidad>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OportunidadState = Readonly<typeof initialState>;

// Reducer

export default (state: OportunidadState = initialState, action): OportunidadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OPORTUNIDAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OPORTUNIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OPORTUNIDAD):
    case REQUEST(ACTION_TYPES.UPDATE_OPORTUNIDAD):
    case REQUEST(ACTION_TYPES.DELETE_OPORTUNIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OPORTUNIDAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OPORTUNIDAD):
    case FAILURE(ACTION_TYPES.CREATE_OPORTUNIDAD):
    case FAILURE(ACTION_TYPES.UPDATE_OPORTUNIDAD):
    case FAILURE(ACTION_TYPES.DELETE_OPORTUNIDAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPORTUNIDAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPORTUNIDAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OPORTUNIDAD):
    case SUCCESS(ACTION_TYPES.UPDATE_OPORTUNIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OPORTUNIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/oportunidads';

// Actions

export const getEntities: ICrudGetAllAction<IOportunidad> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OPORTUNIDAD_LIST,
  payload: axios.get<IOportunidad>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOportunidad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OPORTUNIDAD,
    payload: axios.get<IOportunidad>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOportunidad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OPORTUNIDAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOportunidad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OPORTUNIDAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOportunidad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OPORTUNIDAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
