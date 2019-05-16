import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGasto, defaultValue } from 'app/shared/model/gasto.model';

export const ACTION_TYPES = {
  FETCH_GASTO_LIST: 'gasto/FETCH_GASTO_LIST',
  FETCH_GASTO: 'gasto/FETCH_GASTO',
  CREATE_GASTO: 'gasto/CREATE_GASTO',
  UPDATE_GASTO: 'gasto/UPDATE_GASTO',
  DELETE_GASTO: 'gasto/DELETE_GASTO',
  RESET: 'gasto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGasto>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type GastoState = Readonly<typeof initialState>;

// Reducer

export default (state: GastoState = initialState, action): GastoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GASTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GASTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GASTO):
    case REQUEST(ACTION_TYPES.UPDATE_GASTO):
    case REQUEST(ACTION_TYPES.DELETE_GASTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GASTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GASTO):
    case FAILURE(ACTION_TYPES.CREATE_GASTO):
    case FAILURE(ACTION_TYPES.UPDATE_GASTO):
    case FAILURE(ACTION_TYPES.DELETE_GASTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GASTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_GASTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GASTO):
    case SUCCESS(ACTION_TYPES.UPDATE_GASTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GASTO):
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

const apiUrl = 'api/gastos';

// Actions

export const getEntities: ICrudGetAllAction<IGasto> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GASTO_LIST,
  payload: axios.get<IGasto>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IGasto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GASTO,
    payload: axios.get<IGasto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGasto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GASTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGasto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GASTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGasto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GASTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
